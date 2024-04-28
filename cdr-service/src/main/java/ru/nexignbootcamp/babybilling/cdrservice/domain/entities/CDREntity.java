package ru.nexignbootcamp.babybilling.cdrservice.domain.entities;

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
@Table(name = "transactions")
public class CDREntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String callType;

    private Long clientMsisdn;

    private Long targetMsisdn;

    private Long startTime;

    private Long endTime;

}
