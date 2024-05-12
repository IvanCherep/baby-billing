package ru.nexignbootcamp.babybilling.brtservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.brtservice.domain.dto.AccountRefillDto;
import ru.nexignbootcamp.babybilling.brtservice.domain.dto.ManagerWillDto;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.DataPlanEntity;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.MsisdnEntity;
import ru.nexignbootcamp.babybilling.brtservice.repositories.DataPlanRepository;
import ru.nexignbootcamp.babybilling.brtservice.repositories.MsisdnRepository;

@Service
@Slf4j
public class ClientManagementService {

    private MsisdnRepository msisdnRepository;

    private DataPlanRepository dataPlanRepository;

    public ClientManagementService(MsisdnRepository msisdnRepository, DataPlanRepository dataPlanRepository) {
        this.msisdnRepository = msisdnRepository;
        this.dataPlanRepository = dataPlanRepository;
    }

    public boolean isClientExist(Long msisdn) {
        return msisdnRepository.existsById(msisdn);
    }

    public AccountRefillDto refill(Long msisdn, AccountRefillDto accountRefillDto) {
        accountRefillDto.setMsisdn(msisdn);
        MsisdnEntity msisdnEntity = msisdnRepository.findById(msisdn).get();
        Float accountRefillAmount = accountRefillDto.getMoney();
        Float msisdnRefilledMoney = msisdnEntity.getRemainingMoney() + accountRefillAmount;
        msisdnEntity.setRemainingMoney(msisdnRefilledMoney);
        msisdnRepository.save(msisdnEntity);
        accountRefillDto.setMoney(msisdnRefilledMoney);
        log.info(
                String.format("Msisdn +%d has been refilled by %.2f y.e. Current balance: %.2f",
                        msisdn,
                        accountRefillAmount,
                        msisdnRefilledMoney)
        );
        return accountRefillDto;
    }

    public void changeTariff(ManagerWillDto managerWillDto) {
        MsisdnEntity msisdnEntity = msisdnRepository.findById(managerWillDto.getMsisdn()).get();
        DataPlanEntity newDataPlan = dataPlanRepository.findById(managerWillDto.getTariffId()).get();
        if (managerWillDto.getTariffId() == msisdnEntity.getDataPlan().getId()) {
            log.info(String.format("The tariff has not been changed. Msisdn: +%d already uses the tariff with id %d.", managerWillDto.getMsisdn(), managerWillDto.getTariffId()));
            return;
        }
        msisdnEntity.setDataPlan(newDataPlan);
        msisdnRepository.save(msisdnEntity);
        log.info(String.format("The tariff for msisdn: +%d has been successfully changed. Money for tariff will be debited when charging" +
                "next CDR of this number.", msisdnEntity.getMsisdn()));
    }

    public boolean isDataPlanExist(Integer tariffId) {
        return dataPlanRepository.existsById(tariffId);
    }
}
