package com.duvi.services.stats.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Table(name = "items")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = DataPoint.class)
    @JoinColumns({
            @JoinColumn(name = "account_name"),
            @JoinColumn(name = "data_date")

    })
    private DataPoint dataPoint;
    private String title;
    private BigDecimal amount;
    private Currency currency;
    private Frequency frequency;
    private ItemType type;

}
