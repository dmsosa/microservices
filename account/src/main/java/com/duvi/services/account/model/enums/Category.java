package com.duvi.services.account.model.enums;

public enum Category {
    FIXED, OCCASIONAL;
    public static Category[] ALL = {FIXED, OCCASIONAL};
    public static Category getDefault() {return FIXED;}
}
