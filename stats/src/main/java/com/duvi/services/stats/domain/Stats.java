package com.duvi.services.stats.domain;

import com.duvi.services.stats.domain.enums.Currency;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "stats")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;

    private LocalDateTime statsDate;

    @OneToMany(mappedBy = "stats", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Item> items;

    private BigDecimal totalIncomes;

    private BigDecimal totalExpenses;
    private Currency currency;


    //todo rates
}
