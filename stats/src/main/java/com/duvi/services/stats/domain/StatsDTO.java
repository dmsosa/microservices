package com.duvi.services.stats.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record StatsDTO(Long id, String accountName, LocalDateTime statsDate, List<Item> incomes, List<Item> expenses, Item saving, BigDecimal totalIncomes, BigDecimal totalExpenses) {
}
