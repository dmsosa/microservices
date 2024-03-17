package com.duvi.services.stats.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Table(name = "stats")
@Entity
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
    @JsonIgnore
    private Datapoint datapoint;
    private BigDecimal totalIncomes;
    private BigDecimal totalExpenses;
    private BigDecimal totalSavings;

    //todo rates
}
