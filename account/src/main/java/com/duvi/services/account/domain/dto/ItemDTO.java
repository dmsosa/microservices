package com.duvi.services.account.domain.dto;

import com.duvi.services.account.domain.Currency;
import com.duvi.services.account.domain.Frequency;
import com.duvi.services.account.domain.ItemType;

import java.math.BigDecimal;

public record ItemDTO(
        String accountName,
        String title,
        BigDecimal amount,
        Currency currency,
        Frequency frequency,
        ItemType type,
        String icon
){
}
