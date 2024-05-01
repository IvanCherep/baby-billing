package ru.nexignbootcamp.babybilling.hrsservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
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