package com.duvi.gateway.service;

import com.duvi.gateway.model.AccountDTO;
import com.duvi.gateway.model.StatsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class StatsServiceImpl implements StatsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebClient.Builder webClientBuilder;
    private ReactiveCircuitBreaker reactiveCircuitBreaker;
    public StatsServiceImpl(WebClient.Builder webClientBuilder, ReactiveCircuitBreaker reactiveCircuitBreaker) {
        this.webClientBuilder = webClientBuilder;
        this.reactiveCircuitBreaker = reactiveCircuitBreaker;
    }
    
    @Override
    public Flux<StatsDTO> getStatsOfAccount(String accountName) {
        return reactiveCircuitBreaker.run(webClientBuilder.build()
                        .get()
                        .uri("/stats/" + accountName)
                        .retrieve()
                        .bodyToFlux(StatsDTO.class)
                        .switchIfEmpty(Flux.just(new StatsDTO())), throwable -> getStatsFallback());
    }

    @Override
    public Flux<StatsDTO> saveStatsOfAccount(AccountDTO account) {
        return reactiveCircuitBreaker.run(webClientBuilder.build()
                        .post()
                        .uri("/stats/save")
                        .bodyValue(account)
                        .retrieve()
                        .bodyToFlux(StatsDTO.class), throwable -> getStatsFallback());
    }
    @Override
    public Flux<StatsDTO> getStatsFallback() {
        return Flux.just(new StatsDTO());
    }

}
