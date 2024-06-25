package com.duvi.services.stats.service;

import com.duvi.services.stats.domain.dto.AccountDTO;
import com.duvi.services.stats.domain.Stats;
import com.duvi.services.stats.domain.dto.ItemDTO;
import com.duvi.services.stats.domain.dto.StatsDTO;
import com.duvi.services.stats.domain.exception.EntityExistsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface StatsService {
    StatsDTO createDTO(Stats stats);
    Flux<StatsDTO> getStatsOfAccountByName(String accountName);
    Mono<Void> createStats(AccountDTO account) throws EntityExistsException;
    BigDecimal calculateTotal(List<ItemDTO> items);
}
