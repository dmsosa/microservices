package com.duvi.authservice.controller;

import com.duvi.authservice.config.TokenService;
import com.duvi.authservice.model.*;
import com.duvi.authservice.model.exception.UserExistsException;
import com.duvi.authservice.model.exception.UserNotExistsException;
import com.duvi.authservice.repository.UserRepository;
import com.duvi.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring6.view.ThymeleafView;

import java.security.Principal;

@Slf4j
@RestController
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private UserService userService;
    private AuthenticationManager authenticationManager;


    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/current")
    public ResponseEntity<Principal> getUser(Principal principal) {
        return new ResponseEntity<>(principal, HttpStatus.OK);
    }

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody User user) throws UserExistsException {
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
