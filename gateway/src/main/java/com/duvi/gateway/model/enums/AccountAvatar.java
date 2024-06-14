package com.duvi.gateway.model.enums;

public enum AccountAvatar {
    PIGGY("piggy"), BEAR("bear"), FOX("fox"), BOSS("boss"), COW("cow"), SECRETARY("secretary"), HORSE("horse"), RABBIT("rabbit"), ELEPHANT("elephant"), TURTLE("turtle");
    private String name;
    AccountAvatar(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public static final AccountAvatar[] ALL = {PIGGY, BEAR, FOX, BOSS, COW, SECRETARY, HORSE, RABBIT, ELEPHANT, TURTLE};
}
