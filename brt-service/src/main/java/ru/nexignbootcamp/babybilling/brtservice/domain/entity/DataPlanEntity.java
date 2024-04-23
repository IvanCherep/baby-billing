package ru.nexignbootcamp.babybilling.brtservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "data_plans")
public class DataPlanEntity {

    @Id
    private Integer id;

    private String title;

    @Column(name = "ppm_to_native")
    private Float ppmToNative;

    @Column(name = "ppm_from_native")
    private Float ppmFromNative;

    @Column(name = "ppm_to_foreign")
    private Float ppmToForeign;

    @Column(name = "ppm_from_foreign")
    private Float ppmFromForeign;

    @Column(name = "minutes_plan")
    private Integer minutesPlan;

    @Column(name = "monthly_payment")
    private Float monthlyPayment;

}
