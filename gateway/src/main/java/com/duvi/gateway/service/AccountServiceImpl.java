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
                throwable -> {
                    logger.trace("Failure while reaching account API for editing the account, providing fallback value for: ACCOUNT");
                    return Mono.just(account);
                });
    }

    @Override
    public Mono<AccountDTO> getAccountFallback(String accountName) {
        logger.trace("Failure while reaching account API, providing fallback value for: ACCOUNT");
        List<ItemDTO> fallbackIncomes = new ArrayList<>();
        List<ItemDTO> fallbackExpenses = new ArrayList<>();
        ItemDTO i1 = new ItemDTO(1L, accountName, "salary", "work", BigDecimal.valueOf(1200), Category.FIXED, Currency.USD, Frequency.MONTH, Type.INCOME);
        ItemDTO i2 = new ItemDTO(2L, accountName, "freelance", "work", BigDecimal.valueOf(300), Category.FIXED, Currency.USD, Frequency.WEEK, Type.INCOME);
        ItemDTO i3 = new ItemDTO(3L, accountName, "work", "work", BigDecimal.valueOf(50), Category.OCCASIONAL, Currency.USD, Frequency.DAY, Type.INCOME);
        ItemDTO e1 = new ItemDTO(4L, accountName, "aluguer", "house", BigDecimal.valueOf(1200), Category.FIXED, Currency.USD, Frequency.MONTH, Type.INCOME);
        ItemDTO e2 = new ItemDTO(5L, accountName, "gym", "sport", BigDecimal.valueOf(55), Category.FIXED, Currency.USD, Frequency.MONTH, Type.INCOME);
        ItemDTO e3 = new ItemDTO(6L, accountName, "food", "food", BigDecimal.valueOf(250), Category.FIXED, Currency.USD, Frequency.MONTH, Type.INCOME);

        fallbackIncomes.add(i1);
        fallbackIncomes.add(i2);
        fallbackIncomes.add(i3);
        fallbackExpenses.add(e1);
        fallbackExpenses.add(e2);
        fallbackExpenses.add(e3);

        return Mono.just(new AccountDTO(
                accountName,
                LocalDateTime.now(),
                fallbackIncomes,
                fallbackExpenses,
                "I'm using microservices by fallback",
                "cow",
                Currency.getDefault()));
    }
}
