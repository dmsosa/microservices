package com.duvi.services.account.controller;

import com.duvi.services.account.client.StatClient;
import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import com.duvi.services.account.domain.User;
import com.duvi.services.account.domain.dto.ItemDTO;
import com.duvi.services.account.service.AccountService;
import com.duvi.services.account.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.loadbalancer.security.OAuth2LoadBalancerClientAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.List;

//Save an account means to "make its statistics" from its current items
@RefreshScope
@RestController
@RequestMapping("")
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AccountService accountService;
    private ItemService itemService;
    public AccountController(AccountService accountService,
                             ItemService itemService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountName}")
    @PreAuthorize("#accountName.equals('demo')")
    public ResponseEntity<Account> getAccountByName(@PathVariable String accountName) {
        Account account = accountService.getAccountByName(accountName);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody User user) {
        Account account = accountService.createAccount(user);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @PostMapping("/save")
    public void saveChanges(Principal principal) {
        Account account = accountService.getAccountByName(principal.getName());
        accountService.saveChanges(account);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(account.getName(), account.getLastSeen()));
    }
    @PostMapping("/save/{accountName}")
    @PreAuthorize("#accountName.equals('demo'")
    public void saveChanges(@PathVariable String accountName) {
        Account account = accountService.getAccountByName(accountName);
        accountService.saveChanges(account);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(account.getName(), account.getLastSeen()));
    }

    //Item methods
    @GetMapping("/item/{accountName}")
    public ResponseEntity<List<Item>> createItem(@PathVariable String accountName) {
        Account account = accountService.getAccountByName(accountName);
        List<Item> itemList = itemService.getItemsByAccount(account);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }
    @PostMapping("/item/post")
    public ResponseEntity<Item> createItem(@RequestBody ItemDTO itemDTO) {
        Item item = itemService.createItem(itemDTO);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
