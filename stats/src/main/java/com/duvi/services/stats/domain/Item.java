package com.duvi.services.stats.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


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
    @ManyToOne(targetEntity = Datapoint.class)
    @JoinColumns({
            @JoinColumn(name = "account_name"),
            @JoinColumn(name = "data_date")

    })
    private Datapoint datapoint;
    private String title;
    private Category category;
    private BigDecimal amount;
    private Currency currency;
    private Frequency frequency;
    private ItemType type;

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
