package com.duvi.services.stats.client;

import com.duvi.services.stats.domain.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "account",fallback = AccountClientFallback.class)
public interface AccountClient {
    @RequestMapping(method = RequestMethod.GET, value = "api/account/{accountName}")
    Account getAccount(@PathVariable String accountName);
}
