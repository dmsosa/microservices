package com.duvi.services.noti.domain.enums;

import java.util.stream.Stream;

public enum Frequency {
    DAILY(1, "day"), WEEKLY(7, "week"), MONTHLY(30, "month"), QUARTERLY(90, "quarter of the year");

    private int days;
    private String frequencyValue;
    Frequency(int days, String frequencyValue) {
        this.days = days;
        this.frequencyValue = frequencyValue;
    }
    public int getDays() {
        return days;
    };
    public String getFrequencyValue() {
        return frequencyValue;
    };
    public static Frequency withDays(int days) {
        return Stream.of(Frequency.values())
                .filter(f -> f.getDays() == days)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
