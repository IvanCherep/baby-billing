package ru.nexignbootcamp.babybilling.cdrservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
