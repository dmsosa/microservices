package com.duvi.services.noti.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String name;
    private LocalDateTime lastSeen;
    private String note;
    private String icon;
    private List<ItemDTO> incomes;
    private List<ItemDTO> expenses;
}

