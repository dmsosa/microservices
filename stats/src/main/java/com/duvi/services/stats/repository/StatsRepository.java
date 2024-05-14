package com.duvi.services.stats.repository;

import com.duvi.services.stats.domain.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {
    List<Stats> findAllByAccountName(String accountName);

    Optional<Stats> findByAccountNameAndStatsDate(String accountName, LocalDateTime statsDate);

}
