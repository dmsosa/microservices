package com.duvi.gateway.model;

import com.duvi.gateway.model.enums.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private String name;
    private LocalDateTime lastSeen;
    private String note;
    private String icon;
    private List<ItemDTO> incomes;
    private List<ItemDTO> expenses;
    private Currency currency;
}
