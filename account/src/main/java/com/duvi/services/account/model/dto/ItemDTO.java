package com.duvi.services.account.model.dto;

import com.duvi.services.account.model.Category;
import com.duvi.services.account.model.Currency;
import com.duvi.services.account.model.Frequency;
import com.duvi.services.account.model.Type;

import java.math.BigDecimal;

public record ItemDTO(
        String accountName,
        String title,
        String icon,
        BigDecimal amount,
        Currency currency,
        Frequency frequency,
        Type type,
        Category category
){
}
