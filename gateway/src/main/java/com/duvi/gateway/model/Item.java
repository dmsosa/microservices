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
    private String title;
    private String icon;
    private BigDecimal amount;
    private Category category;
    private Currency currency;
    private Frequency frequency;
    private Type type;
}
