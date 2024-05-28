package com.duvi.services.stats.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record StatsDTO(Long id, String accountName, LocalDateTime statsDate, BigDecimal totalIncomes, BigDecimal totalExpenses) {
}
