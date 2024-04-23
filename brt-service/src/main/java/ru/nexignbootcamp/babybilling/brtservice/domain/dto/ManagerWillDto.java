package ru.nexignbootcamp.babybilling.brtservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerWillDto {

    private String login;

    private String password;

    private Long msisdn;

    private Integer tariffId;

}
