package ru.nexignbootcamp.babybilling.brtservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRefillDto {

    private Long msisdn;

    private Float money;

}
