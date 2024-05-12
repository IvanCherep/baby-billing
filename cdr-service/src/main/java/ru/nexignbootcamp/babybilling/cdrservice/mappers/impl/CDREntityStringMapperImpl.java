package ru.nexignbootcamp.babybilling.cdrservice.mappers.impl;

import org.springframework.stereotype.Component;
import ru.nexignbootcamp.babybilling.cdrservice.domain.entities.CDREntity;
import ru.nexignbootcamp.babybilling.cdrservice.mappers.Mapper;

/**
 * Преобразует CDREntity в строку и обратно.
 */
@Component
public class CDREntityStringMapperImpl implements Mapper<CDREntity, String> {

    @Override
    public String mapTo(CDREntity cdrEntity) {
        return cdrEntity.getCallType() + ", " +
                cdrEntity.getClientMsisdn() + ", " +
                cdrEntity.getTargetMsisdn() + ", " +
                cdrEntity.getStartTime() + ", " +
                cdrEntity.getEndTime() + "\n";
    }

    @Override
    public CDREntity mapFrom(String s) {
        String[] parts = s.split(", ");

        String callType = parts[0];
        Long clientMsisdn = Long.parseLong(parts[1]);
        Long targetMsisdn = Long.parseLong(parts[2]);
        Long startTime = Long.parseLong(parts[3]);
        Long endTime = Long.parseLong(parts[4]);

        return CDREntity.builder()
                .callType(callType)
                .clientMsisdn(clientMsisdn)
                .targetMsisdn(targetMsisdn)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
