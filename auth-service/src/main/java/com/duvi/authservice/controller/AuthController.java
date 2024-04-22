package com.duvi.authservice.controller;

import com.duvi.authservice.config.TokenService;
import com.duvi.authservice.model.*;
import com.duvi.authservice.model.exception.UserExistsException;
import com.duvi.authservice.model.exception.UserNotExistsException;
import com.duvi.authservice.repository.UserRepository;
import com.duvi.authservice.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.SessionAttributesHandler;
import org.thymeleaf.spring6.view.ThymeleafView;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private DiscoveryClient discoveryClient;


    public AuthController(UserService userService, AuthenticationManager authenticationManager, DiscoveryClient discoveryClient) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.discoveryClient = discoveryClient;
    }


    @GetMapping("/current")
    public ResponseEntity<Principal> getUser(Principal principal) {
        return new ResponseEntity<>(principal, HttpStatus.OK);
    }

    @PostMapping("/register")
    public void registerUser(@Validated @ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UserExistsException {
        userService.saveUser(user);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("Successfully registered and authenticated");
    }
    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) throws UserNotExistsException {
        userService.deleteUser(username);
    }

}
