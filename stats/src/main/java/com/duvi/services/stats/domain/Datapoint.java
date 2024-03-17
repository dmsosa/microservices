package com.duvi.services.stats.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "datapoints")
@Getter
@Setter
@NoArgsConstructor
public class Datapoint {
    @EmbeddedId
    private DatapointId id;
    @OneToMany(mappedBy = "datapoint")
    private Set<Item> items;
    @OneToOne(mappedBy = "datapoint")
    private Stat stats;


}
