package ru.nexignbootcamp.babybilling.cdrservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CDRDto {

    private Long id;

    private Integer callType;

    private Long clientMsisdn;

    private Long targetMsisdn;

    private Long startTime;

    private Long endTime;

}
