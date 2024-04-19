package com.duvi.gateway.model;

public enum Currency {
    USD, EUR, GBP;
    public static Currency getDefault() {
        return USD;
    }
}
