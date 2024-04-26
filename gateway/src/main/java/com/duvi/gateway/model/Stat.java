package com.duvi.gateway.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Stat {
    private BigDecimal totalIncomes;
    private BigDecimal totalExpenses;
    private BigDecimal totalSavings;
}
