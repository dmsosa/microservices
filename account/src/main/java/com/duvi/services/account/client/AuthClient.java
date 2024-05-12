package com.duvi.services.account.client;

import com.duvi.services.account.domain.User;
import com.duvi.services.account.domain.exception.EntityNotFoundException;
import com.duvi.services.account.domain.exception.EntityExistsException;
import feign.Feign;
import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "auth-service")
public interface AuthClient {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/uaa/create",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void createUser(User user) throws EntityExistsException;
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/uaa/{username}"
    )
    public void deleteUser(@PathVariable("username") String username) throws EntityNotFoundException;

}
