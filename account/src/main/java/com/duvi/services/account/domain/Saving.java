package com.duvi.services.account.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Saving {

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
