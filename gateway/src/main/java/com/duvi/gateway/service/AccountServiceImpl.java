package com.duvi.gateway.service;

import com.duvi.gateway.model.AccountDTO;
import com.duvi.gateway.model.enums.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebClient.Builder webClientBuilder;
    private ReactiveCircuitBreaker reactiveCircuitBreaker;
    public AccountServiceImpl(WebClient.Builder webClientBuilder, ReactiveCircuitBreaker reactiveCircuitBreaker) {
        this.webClientBuilder = webClientBuilder;
        this.reactiveCircuitBreaker = reactiveCircuitBreaker;
    }

    @Override
    public Mono<AccountDTO> getAccount(String accountName) {
        return reactiveCircuitBreaker.run(webClientBuilder
                .build()
                .get()
                .uri("/account/" + accountName)
                .retrieve()
                .bodyToMono(AccountDTO.class)
                .switchIfEmpty( createAccount(accountName) ), throwable -> getAccountFallback(accountName));
    }

    @Override
    public Mono<AccountDTO> createAccount(String accountName) {
        return webClientBuilder.build()
                .post()
                .uri("/account/create/" + accountName)
                .retrieve().bodyToMono(AccountDTO.class);
    }

    @Override
    public Mono<AccountDTO> editAccount(String accountName, AccountDTO account) {
        return reactiveCircuitBreaker.run(webClientBuilder.build()
                .post()
                .uri("/account/edit/" + accountName)
                .bodyValue(account)
                .retrieve()
                .bodyToMono(AccountDTO.class),
                //return the same account that was supposed to edit the old one
                throwable -> Mono.just(account));
    }

    @Override
    public Mono<AccountDTO> editAccountItems(String accountName, AccountDTO account) {
        return reactiveCircuitBreaker.run(webClientBuilder.build()
                .post()
                .uri("/account/items/" + accountName)
                .bodyValue(account)
                .retrieve()
                .bodyToMono(AccountDTO.class),
                throwable -> Mono.just(account));
    }

    @Override
    public Mono<AccountDTO> getAccountFallback(String accountName) {
        return Mono.just(new AccountDTO(
                accountName,
                LocalDateTime.now(),
                null,
                null,
                "I'm using microservices",
                "piggy",
                Currency.getDefault()));
    }
}
