package ru.nexignbootcamp.babybilling.brtservice.domain.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nexignbootcamp.babybilling.brtservice.domain.entity.ClientEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsisdnDto {

    private Long msisdn;

    private Integer dataPlanId;

    private Integer remainingMinutes;

    private Float remainingMoney;

    private ClientEntity client;

    private Long lastPaymentTimeStamp;

}
