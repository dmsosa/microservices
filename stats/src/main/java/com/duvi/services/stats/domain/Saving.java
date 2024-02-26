package com.duvi.services.stats.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class Saving {

    private Currency currency;
    private BigDecimal amount;
    private BigDecimal interest;
    private Boolean deposit;
    private Boolean capitalization;
}
