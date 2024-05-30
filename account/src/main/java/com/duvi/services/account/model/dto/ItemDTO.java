package com.duvi.services.account.model.dto;

import com.duvi.services.account.model.enums.Category;
import com.duvi.services.account.model.enums.Currency;
import com.duvi.services.account.model.enums.Frequency;
import com.duvi.services.account.model.enums.Type;

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
        Type type
){
}
