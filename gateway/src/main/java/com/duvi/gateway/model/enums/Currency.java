package com.duvi.gateway.model.enums;

public enum Currency {
    USD, EUR, GBP;
    public static Currency[] ALL = {USD, EUR, GBP};
    public static Currency getDefault() {
        return USD;
    }
}
