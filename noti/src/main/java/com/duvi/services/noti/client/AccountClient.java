package com.duvi.services.noti.client;


import com.duvi.services.noti.client.fallback.AccountFallbackFactory;
import com.duvi.services.noti.domain.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "account", fallbackFactory = AccountFallbackFactory.class)
public interface AccountClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/account/{accountName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    AccountDTO getAccount(@PathVariable("accountName") String accountName);
}
