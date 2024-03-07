package com.duvi.services.account.client;

import com.duvi.services.account.domain.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "stat-service")
public interface StatClient {
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/stat/",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void saveAccount(Account account);
}
