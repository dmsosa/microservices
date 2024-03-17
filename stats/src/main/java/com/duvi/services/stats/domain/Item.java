package com.duvi.services.stats.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Datapoint datapoint;
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
    public Item(ItemDTO dto) {
        this.title = dto.title();
        this.amount = dto.amount();
        this.category = dto.category();
        this.currency = dto.currency();
        this.frequency = dto.frequency();
        this.type = dto.type();
    }

}
