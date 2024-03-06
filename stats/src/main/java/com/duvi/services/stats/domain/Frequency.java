package com.duvi.services.stats.domain;

import java.math.BigDecimal;

public enum Frequency {
    DAY(1), MONTH(30.4368), QUARTER(91.3106), YEAR(365.2425);

    private double baseRatio;

    Frequency(double baseRatio) { this.baseRatio = baseRatio; }

    public BigDecimal getBaseRatio() { return new BigDecimal(this.baseRatio); }
    public static Frequency getBase() { return DAY; }


}
