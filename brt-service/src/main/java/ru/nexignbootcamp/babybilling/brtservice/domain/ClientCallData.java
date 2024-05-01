package ru.nexignbootcamp.babybilling.brtservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCallData {

    private Long msisdn;

    private Long startTime;

    private Long endTime;

    private Float pricePerMinute;

    private Integer clientRemainingMinutes;

    private Long lastPaymentTimestamp;

    private Float tariffMonthlyFee;

    private Integer tariffMinutesPlan;

}