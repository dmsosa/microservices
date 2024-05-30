package com.duvi.services.noti.repository.converter;

import com.duvi.services.noti.domain.enums.Frequency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FrequencyReaderConverter implements Converter<Integer, Frequency> {
    @Override
    public Frequency convert(Integer source) {
        return Frequency.withDays(source);
    }
}
