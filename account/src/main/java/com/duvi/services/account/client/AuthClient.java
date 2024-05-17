package com.duvi.services.account.client;

import com.duvi.services.account.model.User;
import com.duvi.services.account.model.exception.EntityNotFoundException;
import com.duvi.services.account.model.exception.EntityExistsException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "auth-service")
public interface AuthClient {
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/api/uaa/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public User getUser(@PathVariable("username") String username);
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/uaa/save",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void createUser(@RequestBody User user) throws EntityExistsException;
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/api/uaa/{username}"
    )
    public void deleteUser(@PathVariable("username") String username) throws EntityNotFoundException;

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/api/uaa/{username}"
    )
    public void editUser(@PathVariable("username") String username, @RequestBody User newUser);
}
