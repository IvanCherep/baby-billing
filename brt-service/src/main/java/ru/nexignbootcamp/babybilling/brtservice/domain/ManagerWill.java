package ru.nexignbootcamp.babybilling.brtservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные для смены тарифа.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerWill {

    private String login;

    private String password;

    private Long msisdn;

    private Integer tariffId;

}
