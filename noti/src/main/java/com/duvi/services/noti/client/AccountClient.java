package com.duvi.services.noti.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "account")
public interface AccountClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/account/{accountName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    String getAccount(@PathVariable("accountName") String accountName);
}
