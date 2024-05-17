package com.duvi.services.account.model;

public enum Category {
    FIXED, OCCASIONAL;
    public static Category getDefault() {return FIXED;}
}
