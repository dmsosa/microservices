package com.duvi.gateway.model;

import com.duvi.gateway.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String name;
    private LocalDateTime lastSeen;
    private List<ItemDTO> incomes = new ArrayList<>();
    private List<ItemDTO> expenses = new ArrayList<>();
    private String note;
    private String icon;
    private Currency currency;
}
