package com.duvi.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Item {
    private Long id;
    private String accountName;
    private String title;
    private String icon;
    private BigDecimal amount;
    private Frequency frequency;
    private Type type;
    private Category category;
    private Currency currency;
}
