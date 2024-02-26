package com.duvi.services.account.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Item {

    @ManyToOne
    @JoinColumn(name = "account_name")
    private Account account;
    @NotNull
    @Length(min = 4, max = 20)
    private String title;
    @NotNull

    private BigDecimal amount;
    @NotNull

    private Currency currency;
    @NotNull

    private TimePeriod timePeriod;
    @NotNull

    private String icon;

}
