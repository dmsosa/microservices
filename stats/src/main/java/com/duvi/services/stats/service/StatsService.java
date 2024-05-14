package com.duvi.services.stats.service;

import com.duvi.services.stats.domain.AccountDTO;
import com.duvi.services.stats.domain.Item;
import com.duvi.services.stats.domain.Stats;
import com.duvi.services.stats.domain.exception.EntityExistsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface StatsService {
    Flux<Stats> getStatsOfAccountByName(String accountName);
    Mono<Stats> createStats(AccountDTO account);
    BigDecimal calculateTotal(List<Item> items);
}
