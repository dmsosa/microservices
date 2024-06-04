package com.duvi.gateway.service;

import com.duvi.gateway.model.AccountDTO;
import com.duvi.gateway.model.StatsDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface StatsService {
    Flux<StatsDTO> getStatsOfAccount(String accountName);
    Flux<StatsDTO> saveStatsOfAccount(AccountDTO account);
    Flux<StatsDTO> getStatsFallback();
}
