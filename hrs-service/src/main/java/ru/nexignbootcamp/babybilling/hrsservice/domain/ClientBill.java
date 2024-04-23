package ru.nexignbootcamp.babybilling.hrsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
