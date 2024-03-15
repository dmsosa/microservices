package com.duvi.services.account.domain.converter;

import com.duvi.services.account.domain.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {
    @Override
    public String convertToDatabaseColumn(Category attribute) {
        return attribute.name();
    }

    @Override
    public Category convertToEntityAttribute(String dbData) {
        return Category.valueOf(dbData);
    }
}
