package com.duvi.services.stats.domain;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Item {

    private String accountName;
    private String title;
    private BigDecimal amount;
    private Currency currency;
    private TimePeriod timePeriod;
    private String icon;

}
