package com.duvi.gateway.model.enums;

public enum ItemIcon {

    HOUSE("house"), EDUCATION("education"), ENTERTAINMENT("entertainment"),
    WORK("work"), FOOD("food"), SPORT("sport"), HEALTH("health"), HYGIENE("hygiene"), INVESTMENT("investment");
    private String name;
    ItemIcon(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public static final ItemIcon[] ALL = {HOUSE, EDUCATION, ENTERTAINMENT, WORK, FOOD, SPORT, HEALTH, HYGIENE, INVESTMENT};

}
