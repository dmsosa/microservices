package com.duvi.services.account.model.converter;

import com.duvi.services.account.model.enums.Type;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, String> {
    @Override
    public String convertToDatabaseColumn(Type attribute) {
        return attribute.name();
    }

    @Override
    public Type convertToEntityAttribute(String dbData) {
        return Type.valueOf(dbData);
    }
}

