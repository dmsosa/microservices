package com.duvi.services.account.model;

public enum Currency {
    USD, EUR, GBP;
    public static Currency[] ALL = {USD, EUR, GBP};
    public static Currency getDefault() {
        return USD;
    }
}
