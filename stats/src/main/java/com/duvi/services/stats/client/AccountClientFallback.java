package com.duvi.services.stats.client;

import com.duvi.services.stats.domain.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class AccountClientFallback implements AccountClient {
    public static final Logger logger = LoggerFactory.getLogger(AccountClientFallback.class);
    @Override
    public AccountDTO getAccount(String accountName) {
        logger.info("Feign Client failed, this is a fallback while trying to retrieve account with name %s".formatted(accountName));
        return new AccountDTO();
    }
}
