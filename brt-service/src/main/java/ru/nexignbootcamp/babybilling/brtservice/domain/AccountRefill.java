package ru.nexignbootcamp.babybilling.brtservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные для пополнения счета.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRefill {

    private Long msisdn;

    private Float money;

}
