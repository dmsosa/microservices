package com.duvi.services.account.domain.dto;

import com.duvi.services.account.domain.Currency;
import com.duvi.services.account.domain.Frequency;
import com.duvi.services.account.domain.Type;

import java.math.BigDecimal;

public record ItemDTO(
        String accountName,
        String title,
        String icon,
        BigDecimal amount,
        Currency currency,
        Frequency frequency,
        Type type
){
}
