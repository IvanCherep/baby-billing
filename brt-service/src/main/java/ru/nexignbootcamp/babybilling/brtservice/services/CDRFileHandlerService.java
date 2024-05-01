package ru.nexignbootcamp.babybilling.brtservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nexignbootcamp.babybilling.brtservice.domain.CDR;
import ru.nexignbootcamp.babybilling.brtservice.mappers.impl.CDRMapperImpl;
import ru.nexignbootcamp.babybilling.brtservice.repositories.MsisdnRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
public class CDRFileHandlerService {

    @Autowired
    MsisdnRepository msisdnRepository;

    @Autowired
    CDRMapperImpl cdrMapper;

    @Autowired
    BillingService billingService;

    public void authorize(Path cdrFile) {

        try {
            List<String> strings = Files.readAllLines(cdrFile);
            for (String cdrString : strings) {
                CDR cdr = cdrMapper.mapFrom(cdrString);
                if (msisdnRepository.existsById(cdr.getClientMsisdn())) {
                    billingService.processCDR(cdr);
                }
            }
            Files.delete(cdrFile);
        } catch (IOException e) {
            log.error("Failed to read data from file. " + e.getMessage());
        }

    }
}
