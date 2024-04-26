package com.duvi.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Datapoint {
    private DatapointId id;
    private List<Item> items;
    private Stat stats;
}
