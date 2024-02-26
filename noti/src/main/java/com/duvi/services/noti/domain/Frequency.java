package com.duvi.services.noti.domain;

import java.util.stream.Stream;

public enum Frequency {
    WEEKLY(7), MONTHLY(30), QUARTERLY, YEAR(365);

    private Integer days;
    Frequency(Integer days) {
        this.days = days;
    }
    public Integer getDays() {
        return this.days;
    };
    public static Frequency withDays(Integer days) {
        return Stream.of(Frequency.values())
                .filter(f -> f.getDays() == days)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
