package com.duvi.services.account.model.dto;

import com.duvi.services.account.model.enums.Currency;

import java.time.LocalDateTime;
import java.util.List;

public record AccountDTO(String name, LocalDateTime lastSeen, String note, String icon, List<ItemDTO> incomes, List<ItemDTO> expenses, Currency currency) {
}
