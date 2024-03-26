package com.duvi.services.account.client;

import com.duvi.services.account.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "auth-service")
public interface AuthClient {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/uaa/register",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void createUser(User user);
}
