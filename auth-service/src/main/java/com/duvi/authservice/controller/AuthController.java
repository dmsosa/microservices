package com.duvi.authservice.controller;

import com.duvi.authservice.config.TokenService;
import com.duvi.authservice.model.*;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;


    @GetMapping("/current")
    public ResponseEntity<Principal> getUser(Principal principal) {
        return new ResponseEntity<>(principal, HttpStatus.OK);
    }

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody User user) throws Exception {
        userService.saveUser(user);
    }
}
