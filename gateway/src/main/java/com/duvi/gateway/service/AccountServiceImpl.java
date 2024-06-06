package com.duvi.gateway.service;

import com.duvi.gateway.model.AccountDTO;
import com.duvi.gateway.model.ItemDTO;
import com.duvi.gateway.model.enums.Category;
import com.duvi.gateway.model.enums.Currency;
import com.duvi.gateway.model.enums.Frequency;
import com.duvi.gateway.model.enums.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        List<ItemDTO> fallbackIncomes = new ArrayList<>();
        ItemDTO i1 = new ItemDTO(1L, accountName, "aluguer", "house", BigDecimal.valueOf(1200), Category.FIXED, Currency.USD, Frequency.MONTH, Type.INCOME);
        ItemDTO i2 = new ItemDTO(2L, accountName, "gym", "gym", BigDecimal.valueOf(300), Category.FIXED, Currency.USD, Frequency.MONTH, Type.INCOME);
        ItemDTO i3 = new ItemDTO(3L, accountName, "work", "work", BigDecimal.valueOf(50), Category.FIXED, Currency.USD, Frequency.MONTH, Type.INCOME);
        return Mono.just(new AccountDTO(
                accountName,
                LocalDateTime.now(),
                fallbackIncomes,
                null,
                "I'm using microservices",
                "cow",
                Currency.getDefault()));
    }
}
