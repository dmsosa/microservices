package com.duvi.services.stats.domain;


import com.duvi.services.stats.domain.enums.Category;
import com.duvi.services.stats.domain.enums.Currency;
import com.duvi.services.stats.domain.enums.Frequency;
import com.duvi.services.stats.domain.enums.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table(name = "items")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "stats_id")
    @JsonIgnore
    private Stats stats;
    private LocalDateTime statsDate;

    private String title;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    public Item(
            String title,
            BigDecimal amount,
            Category category,
            Currency currency,
            Frequency frequency,
            ItemType type
    ) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.currency = currency;
        this.frequency = frequency;
        this.type = type;
    }
    public Item(
            String title,
            Category category,
            Currency currency,
            Frequency frequency,
            ItemType type
    ) {
        this.title = title;
        this.category = category;
        this.currency = currency;
        this.frequency = frequency;
        this.type = type;
    }

}
