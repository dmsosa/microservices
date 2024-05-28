package com.duvi.services.stats.domain.enums;

public enum Currency {
    USD, EUR, GBP;
    public static Currency getDefault() {
        return USD;
    }
}
