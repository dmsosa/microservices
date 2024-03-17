package com.duvi.services.stats.domain;

import java.math.BigDecimal;

public record ItemDTO (String title, BigDecimal amount, Category category, Currency currency, Frequency frequency, ItemType type){
}
