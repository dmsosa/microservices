package com.duvi.services.stats.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ExchangeRateContainer {
    private LocalDateTime date = LocalDateTime.now();
    private Currency base;
    private Map<String, BigDecimal> rates;

    @Override
    public String toString() {
        return "RateList{" +
                "date=" + date +
                ", base=" + base +
                ", rates=" + rates +
                "}";
    }
}
