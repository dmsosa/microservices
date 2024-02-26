package com.duvi.services.account.domain;

public enum Currency {
    USD, EUR, GBP;
    public static Currency getDefault() {
        return USD;
    }
}
