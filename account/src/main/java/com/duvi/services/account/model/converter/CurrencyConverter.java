package com.duvi.services.account.model.converter;

import com.duvi.services.account.model.Currency;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CurrencyConverter implements AttributeConverter<Currency, String> {
    @Override
    public String convertToDatabaseColumn(Currency attribute) {
        return attribute.name();
    }

    @Override
    public Currency convertToEntityAttribute(String dbData) {
        return Currency.valueOf(dbData);
    }
}
