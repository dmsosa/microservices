package com.duvi.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatsDTO {
    private Long id;
    private String accountName;
    private LocalDateTime statsDate;
    private BigDecimal totalIncomes;
    private BigDecimal totalExpenses;
}
