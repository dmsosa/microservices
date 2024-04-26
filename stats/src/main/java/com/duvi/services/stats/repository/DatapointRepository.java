package com.duvi.services.stats.repository;

import com.duvi.services.stats.domain.Datapoint;
import com.duvi.services.stats.domain.DatapointId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatapointRepository extends JpaRepository<Datapoint, DatapointId> {
}
