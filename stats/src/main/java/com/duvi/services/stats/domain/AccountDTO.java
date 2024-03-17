package com.duvi.services.stats.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AccountDTO {
    private String name;
    private LocalDateTime lastSeen;
    private List<ItemDTO> items;
    private String note;
}
