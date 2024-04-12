package com.duvi.authservice.web.convertor;

import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {

    private MessageSource messageSource;

    public DateFormatter(MessageSource messageSource) {
        super();
        this.messageSource = messageSource;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return null;
    }

    @Override
    public String print(Date object, Locale locale) {
        return null;
    }
    private SimpleDateFormat createDateFormat(final Locale locale) {
        String format = this.messageSource.getMessage("date.format", null, locale);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}
