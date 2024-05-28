package com.duvi.services.stats.domain.dto;

import com.duvi.services.stats.domain.enums.Category;
import com.duvi.services.stats.domain.enums.Currency;
import com.duvi.services.stats.domain.enums.Frequency;
import com.duvi.services.stats.domain.enums.ItemType;

import java.math.BigDecimal;

public record ItemDTO(
        Long id,
        String accountName,
        String title,
        String icon,
        BigDecimal amount,
        Category category,
        Currency currency,
        Frequency frequency,
        ItemType type) {
}
