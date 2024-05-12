package ru.nexignbootcamp.babybilling.brtservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные о списание денег и минут с клиента от HRS-сервиса.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientBill {

    private Long msisdn;

    private Integer remainingMinutes;

    private Float moneyBill;

    private Long lastPaymentTimestamp;


}
