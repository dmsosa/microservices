package com.duvi.services.account.domain.dto;

import com.duvi.services.account.domain.Item;

import java.time.LocalDateTime;
import java.util.List;

public record AccountDTO(String name, LocalDateTime lastSeen, String note, List<Item> incomes, List<Item> expenses) {
}
