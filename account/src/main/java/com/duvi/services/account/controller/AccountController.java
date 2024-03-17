package com.duvi.services.account.controller;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.User;
import com.duvi.services.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("")
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountName}")
    public ResponseEntity<Account> getAccountByName(@PathVariable String accountName) {
        Account account = accountService.getAccountByName(accountName);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @PostMapping("/demo")
    public ResponseEntity<String> saveDemoAccount() {
        Account demo = accountService.getAccountByName("demo");
        accountService.saveChanges(demo);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(demo.getName(), demo.getLastSeen()));
        return new ResponseEntity<>("Changes for account %1$s at %2$s saved successfully".formatted(demo.getName(), demo.getLastSeen()), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Account> createAccount(@RequestBody User user) {
        Account account = accountService.createAccount(user);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @PostMapping("/save")
    public void saveChanges(@RequestBody Account account) {
        accountService.saveChanges(account);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(account.getName(), account.getLastSeen()));
    }
}
