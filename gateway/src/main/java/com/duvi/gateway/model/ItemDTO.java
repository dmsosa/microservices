package com.duvi.gateway.model;

import com.duvi.gateway.model.enums.Category;
import com.duvi.gateway.model.enums.Currency;
import com.duvi.gateway.model.enums.Frequency;
import com.duvi.gateway.model.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    private String accountName;
    private String title;
    private String icon;
    private BigDecimal amount;
    private Category category;
    private Currency currency;
    private Frequency frequency;
    private Type type;
}
