package ru.nexignbootcamp.babybilling.brtservice.services;

import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.brtservice.domain.dto.AccountRefillDto;
import ru.nexignbootcamp.babybilling.brtservice.domain.dto.ManagerWillDto;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.MsisdnEntity;
import ru.nexignbootcamp.babybilling.brtservice.repositories.DataPlanRepository;
import ru.nexignbootcamp.babybilling.brtservice.repositories.MsisdnRepository;

@Service
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
        Float msisdnRefilledMoney = msisdnEntity.getRemainingMoney() + accountRefillDto.getMoney();
        msisdnEntity.setRemainingMoney(msisdnRefilledMoney);
        msisdnRepository.save(msisdnEntity);
        accountRefillDto.setMoney(msisdnRefilledMoney);
        return accountRefillDto;
    }

    public void changeTariff(ManagerWillDto managerWillDto) {
        MsisdnEntity msisdnEntity = msisdnRepository.findById(managerWillDto.getMsisdn()).get();
        msisdnEntity.setDataPlan(dataPlanRepository.findById(managerWillDto.getTariffId()).get());
        msisdnRepository.save(msisdnEntity);
    }

    public boolean isDataPlanExist(Integer tariffId) {
        return dataPlanRepository.existsById(tariffId);
    }
}
