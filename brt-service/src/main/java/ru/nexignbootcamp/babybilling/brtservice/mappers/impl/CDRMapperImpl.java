package ru.nexignbootcamp.babybilling.brtservice.mappers.impl;

import org.springframework.stereotype.Component;
import ru.nexignbootcamp.babybilling.brtservice.domain.CDR;
import ru.nexignbootcamp.babybilling.brtservice.mappers.Mapper;

@Component
public class CDRMapperImpl implements Mapper<CDR, String> {

    @Override
    public String mapTo(CDR cdrEntity) {
        return cdrEntity.getCallType() + ", " +
                cdrEntity.getClientMsisdn() + ", " +
                cdrEntity.getTargetMsisdn() + ", " +
                cdrEntity.getStartTime() + ", " +
                cdrEntity.getEndTime() + "\n";
    }

    @Override
    public CDR mapFrom(String s) {
        String[] parts = s.split(", ");

        String callType = parts[0];
        Long clientMsisdn = Long.parseLong(parts[1]);
        Long targetMsisdn = Long.parseLong(parts[2]);
        Long startTime = Long.parseLong(parts[3]);
        Long endTime = Long.parseLong(parts[4]);

        return CDR.builder()
                .callType(callType)
                .clientMsisdn(clientMsisdn)
                .targetMsisdn(targetMsisdn)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
