package com.duvi.services.account.client;

import com.duvi.services.account.domain.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "stats", configuration = {StatClientConfig.class})
public interface StatClient {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/stats/save"
    )
    public void saveAccount(Account account);
}
