package com.duvi.services.noti.client.fallback;

import com.duvi.services.noti.client.AccountClient;
import com.duvi.services.noti.domain.dto.AccountDTO;
import com.duvi.services.noti.domain.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AccountFallbackFactory implements FallbackFactory<AccountFallbackFactory.AccountClientFallback> {

    @Override
    public AccountClientFallback create(Throwable cause) {
        return new AccountClientFallback(cause);
    }
    static class AccountClientFallback implements AccountClient {

        private String message;
        public AccountClientFallback(Throwable cause) {
            this.message = cause.getMessage();
        }
        private Logger logger = LoggerFactory.getLogger(AccountClientFallback.class);
        @Override
        public AccountDTO getAccount(String accountName) {
            logger.error("Error during account fetching of account: {} due to {}", accountName, message != null ? message : "unknown exception");
            return new AccountDTO("null", null, "null", "null", new ArrayList<ItemDTO>(), new ArrayList<ItemDTO>());
        }
    }

}
