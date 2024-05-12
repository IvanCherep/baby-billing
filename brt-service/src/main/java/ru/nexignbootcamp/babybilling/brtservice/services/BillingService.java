package ru.nexignbootcamp.babybilling.brtservice.services;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nexignbootcamp.babybilling.brtservice.domain.CDR;
import ru.nexignbootcamp.babybilling.brtservice.domain.ClientBill;
import ru.nexignbootcamp.babybilling.brtservice.domain.ClientCallData;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.ClientEntity;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.DataPlanEntity;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.MsisdnEntity;
import ru.nexignbootcamp.babybilling.brtservice.repositories.MsisdnRepository;

import java.util.Optional;

/**
 * Сервис принимат запись о звонке, собират данные о клиенте,
 * который совершившил звонок, и передает эти данные для оценки
 * в HRS-сервис. На основе полученных данных изменяет баланс.
 */
@Service
@Slf4j
public class BillingService {

    private MsisdnRepository msisdnRepository;

    private RestTemplate restTemplate;

    private Environment environment;

    private String calculateUrl;

    public BillingService(MsisdnRepository msisdnRepository, RestTemplate restTemplate, Environment environment) {
        this.msisdnRepository = msisdnRepository;
        this.restTemplate = restTemplate;
        this.environment = environment;
        calculateUrl = environment.getProperty("HRS_SERVICE_CALCULATE_PERSON_URL");
    }

    public void processCDR(CDR cdr) {

        MsisdnEntity msisdnEntity = msisdnRepository.findById(cdr.getClientMsisdn()).get();

        Float pricePerMinute = (float) 0;

        if (cdr.getCallType().equals("01") && msisdnRepository.existsById(cdr.getTargetMsisdn())) {
            // Исходящий абонентам "Ромашки"
            pricePerMinute = msisdnEntity.getDataPlan().getPpmToNative();
        } else if (cdr.getCallType().equals("01") && !msisdnRepository.existsById(cdr.getTargetMsisdn())) {
            // Исходящий абонентам других операторов
            pricePerMinute = msisdnEntity.getDataPlan().getPpmToForeign();
        } else if (cdr.getCallType().equals("02") && msisdnRepository.existsById(cdr.getTargetMsisdn())) {
            // Входящий от абонента "Ромашки"
            pricePerMinute = msisdnEntity.getDataPlan().getPpmFromNative();
        } else if (cdr.getCallType().equals("02") && !msisdnRepository.existsById(cdr.getTargetMsisdn())) {
            // Входящий от абонента другого оператора
            pricePerMinute = msisdnEntity.getDataPlan().getPpmFromForeign();
        }

        ClientCallData clientCallData = ClientCallData.builder()
                .msisdn(cdr.getClientMsisdn())
                .startTime(cdr.getStartTime())
                .endTime(cdr.getEndTime())
                .pricePerMinute(pricePerMinute)
                .clientRemainingMinutes(msisdnEntity.getRemainingMinutes())
                .lastPaymentTimestamp(msisdnEntity.getLastPaymentTimeStamp())
                .tariffMonthlyFee(msisdnEntity.getDataPlan().getMonthlyPayment())
                .tariffMinutesPlan(msisdnEntity.getDataPlan().getMinutesPlan())
                .build();

        ResponseEntity<ClientBill> clientBillResponseEntity = restTemplate.postForEntity(calculateUrl, clientCallData, ClientBill.class);
        ClientBill clientBill = clientBillResponseEntity.getBody();
        msisdnEntity.setRemainingMinutes(clientBill.getRemainingMinutes());
        Float msisdnEntityRemainingMoney = msisdnEntity.getRemainingMoney() - clientBill.getMoneyBill();
        msisdnEntity.setRemainingMoney(msisdnEntityRemainingMoney);
        msisdnEntity.setLastPaymentTimeStamp(clientBill.getLastPaymentTimestamp());
        msisdnRepository.save(msisdnEntity);

        }

}
