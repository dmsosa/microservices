package com.duvi.services.noti.repository.converter;

import com.duvi.services.noti.domain.enums.Frequency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FrequencyWriterConverter implements Converter<Frequency, Integer> {

    @Override
    public Integer convert(Frequency source) {
        return source.getDays();
    }
}
