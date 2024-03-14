package com.duvi.services.account.controller;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.User;
import com.duvi.services.account.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Value("${message: def}")
    private String message;
    private AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountName}")
    public ResponseEntity<Account> getAccountByName(@PathVariable String accountName) {
        System.out.println(message);
        Account account = accountService.getAccountByName(accountName);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Account> createAccount(@RequestBody User user) {
        Account account = accountService.createAccount(user);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
