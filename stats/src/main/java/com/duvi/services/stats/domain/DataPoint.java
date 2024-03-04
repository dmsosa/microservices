package com.duvi.services.stats.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "datapoints")
@Getter
@Setter
public class DataPoint {
    @EmbeddedId
    private DataPointId id;

    @OneToMany(mappedBy = "datapoint")
    private Set<Item> incomes;
    @OneToMany(mappedBy = "datapoint")
    private Set<Item> expenses;
    @OneToOne(mappedBy = "datapoint")
    private Stat stats;


}
