package com.duvi.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private String name;
    private LocalDateTime lastSeen;
    private String note;
    private String icon;
    private List<Item> incomes;
    private List<Item> expenses;
}
