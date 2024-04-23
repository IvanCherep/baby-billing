package ru.nexignbootcamp.babybilling.brtservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataPlanDto {

    private Integer id;

    private String title;

    private Float ppmToNative;

    private Float ppmFromNative;

    private Float ppmToForeign;

    private Float ppmFromForeign;

    private Integer minutesPlan;

    private Float monthlyPayment;

}
