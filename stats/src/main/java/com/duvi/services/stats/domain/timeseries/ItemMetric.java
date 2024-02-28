package com.duvi.services.stats.domain.timeseries;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class ItemMetric {

    private String title;
    private BigDecimal amount;

    public ItemMetric(String title, BigDecimal amount) {
        this.title = title;
        this.amount = amount;
    }
}
