package ru.nexignbootcamp.babybilling.brtservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "msisdns")
public class MsisdnEntity {

    @Id
    private Long msisdn;

    @ManyToOne
    @JoinColumn(name = "data_plan_id")
    private DataPlanEntity dataPlan;

    @Column(name = "remaining_minutes")
    private Integer remainingMinutes;

    @Column(name = "remaining_money")
    private Float remainingMoney;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @Column(name = "last_payment_timestamp")
    private Long lastPaymentTimeStamp;

}
