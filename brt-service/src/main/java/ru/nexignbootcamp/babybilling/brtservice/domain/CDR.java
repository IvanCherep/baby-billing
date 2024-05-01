package ru.nexignbootcamp.babybilling.brtservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CDR {

    private String callType;

    private Long clientMsisdn;

    private Long targetMsisdn;

    private Long startTime;

    private Long endTime;

}
