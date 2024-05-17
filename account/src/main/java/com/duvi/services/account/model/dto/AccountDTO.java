package com.duvi.services.account.model.dto;

import com.duvi.services.account.model.Item;

import java.time.LocalDateTime;
import java.util.List;

public record AccountDTO(String name, LocalDateTime lastSeen, String note, String icon, List<Item> incomes, List<Item> expenses) {
}
