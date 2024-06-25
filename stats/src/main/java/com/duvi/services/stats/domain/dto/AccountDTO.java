package com.duvi.services.stats.domain.dto;


import com.duvi.services.stats.domain.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AccountDTO {
    private String name;
    private LocalDateTime lastSeen;
    private List<ItemDTO> incomes;
    private List<ItemDTO> expenses;
    private String note;
    private Currency currency;
}
