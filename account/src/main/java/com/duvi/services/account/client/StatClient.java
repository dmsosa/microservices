package com.duvi.services.account.client;

import com.duvi.services.account.model.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "stats")
public interface StatClient {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/stats/save"
    )
    public void saveAccount(AccountDTO account);
}
