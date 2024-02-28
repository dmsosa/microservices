package com.duvi.services.account.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "savings")
@Getter
@Setter
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "account_name")
    Account account;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currency;
    @NotNull

    private BigDecimal interest;
    @NotNull

    private Boolean deposit;
    @NotNull

    private Boolean capitalization;
}
