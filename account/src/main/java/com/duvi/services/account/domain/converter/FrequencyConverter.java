package com.duvi.services.account.domain.converter;

import com.duvi.services.account.domain.Frequency;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FrequencyConverter implements AttributeConverter<Frequency, String> {
    @Override
    public String convertToDatabaseColumn(Frequency attribute) {
        return attribute.name();
    }

    @Override
    public Frequency convertToEntityAttribute(String dbData) {
        return Frequency.valueOf(dbData);
    }
}
