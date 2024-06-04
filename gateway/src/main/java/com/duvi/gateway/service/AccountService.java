package com.duvi.gateway.service;

import com.duvi.gateway.model.AccountDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AccountService {
    Mono<AccountDTO> getAccount(String accountName);
    Mono<AccountDTO> createAccount(String accountName);
    Mono<AccountDTO> editAccount(String accountName, AccountDTO account);
    Mono<AccountDTO> editAccountItems(String accountName, AccountDTO account);
    Mono<AccountDTO> getAccountFallback(String accountName);

}
