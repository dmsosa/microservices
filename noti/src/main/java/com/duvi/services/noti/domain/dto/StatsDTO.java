package com.duvi.services.noti.domain.dto;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
public class StatsDTO {
    private Long id;
    private String accountName;
    private LocalDateTime statsDate;
    private BigDecimal totalIncomes;
    private BigDecimal totalExpenses;
}
