package ru.nexignbootcamp.babybilling.cdrservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRefill {

    private Long msisdn;

    private Float money;

}