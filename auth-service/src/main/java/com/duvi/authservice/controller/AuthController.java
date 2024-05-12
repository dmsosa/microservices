package com.duvi.authservice.controller;

import com.duvi.authservice.client.AccountClient;
import com.duvi.authservice.model.*;
import com.duvi.authservice.model.exception.UserExistsException;
import com.duvi.authservice.model.exception.UserNotExistsException;
import com.duvi.authservice.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.InMemoryWebSessionStore;


import java.security.Principal;

@Slf4j
@RestController
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private DiscoveryClient discoveryClient;
    private AccountClient accountClient;
    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private SecurityContextHolderStrategy contextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          AccountClient accountClient,
                          DiscoveryClient discoveryClient) {
        this.accountClient = accountClient;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.discoveryClient = discoveryClient;
    }


    @GetMapping("/current")
    public ResponseEntity<Principal> getUser(Principal principal) {
        return new ResponseEntity<>(principal, HttpStatus.OK);
    }

    @PostMapping(path = {"/register"}, consumes = "application/x-www-form-urlencoded")
    public void registerUser(@Validated @ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UserExistsException {
        //Saving user and creating account
        userService.saveUser(user);
        accountClient.createAccount(user.getUsername());

        //Setting authentication
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);


        //Finding gateway instances
        ServiceInstance gatewayInstance = discoveryClient.getInstances("gateway").getFirst();
        String redirectURL = gatewayInstance.getUri() + "/uaaRedirect";

        logger.info("Successfully registered and authenticated" + gatewayInstance.getUri());

        session.setAttribute(SPRING_SECURITY_CONTEXT, context);

        //storing SecurityContext in Repository
        contextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        //redirect
        response.setStatus(302);
        response.setHeader("Location", redirectURL);

    }

    @PutMapping("/{username}")
    public void editUser(@PathVariable String username, User newUser) {
        userService.updateUser(username, newUser);
    }
    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) throws UserNotExistsException {
        userService.deleteUser(username);
    }

}
