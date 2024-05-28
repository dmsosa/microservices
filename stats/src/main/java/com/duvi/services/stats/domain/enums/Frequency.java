package com.duvi.services.stats.domain.enums;

import java.math.BigDecimal;

public enum Frequency {
    DAY(30), WEEK(4.29), MONTH(1);

    private double repetition;

    Frequency(double repetition) { this.repetition = repetition; }

    public BigDecimal getRepetition() { return new BigDecimal(this.repetition); }
    public static Frequency getBase() { return MONTH; }


}
