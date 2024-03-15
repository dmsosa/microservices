package com.duvi.services.account.domain;

public enum Category {
    FIXED, OCCASIONAL;
    public static Category getDefault() {return FIXED;}
}
