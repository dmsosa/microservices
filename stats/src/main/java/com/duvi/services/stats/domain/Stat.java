package com.duvi.services.stats.domain;

import com.duvi.services.stats.domain.DataPoint;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "stats")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "account_name"),
            @JoinColumn(name = "data_date")
    })
    private DataPoint datapoint;
    private BigDecimal totalIncomes;
    private BigDecimal totalExpenses;
    private BigDecimal savings;
    private BigDecimal rate;
}
