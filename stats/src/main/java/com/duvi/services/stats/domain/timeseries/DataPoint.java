package com.duvi.services.stats.domain.timeseries;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "datapoints")
@Getter
@Setter
public class DataPoint {
    @EmbeddedId
    private DataPointId id;

    @OneToMany(mappedBy = "datapoint")
    private Set<ItemMetric> incomes;
    @OneToMany(mappedBy = "datapoint")
    private Set<ItemMetric> expenses;

    private Map<StatsMetrics, BigDecimal> statistics;
    private Map<Currency, BigDecimal> rates;

}
