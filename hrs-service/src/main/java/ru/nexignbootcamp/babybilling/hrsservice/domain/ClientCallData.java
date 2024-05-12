package ru.nexignbootcamp.babybilling.hrsservice.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Данные необходимые сервису для того,
 * чтобы расчитать сколько денег и минут нужно списать с клиента.
 */
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