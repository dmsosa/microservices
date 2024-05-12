package com.duvi.services.account.controller;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import com.duvi.services.account.domain.User;
import com.duvi.services.account.domain.dto.ItemDTO;
import com.duvi.services.account.domain.exception.EntityNotFoundException;
import com.duvi.services.account.domain.exception.EntityExistsException;
import com.duvi.services.account.service.AccountService;
import com.duvi.services.account.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

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
    private DiscoveryClient discoveryClient;
    public AccountController(AccountService accountService,
                             ItemService itemService,
                             DiscoveryClient discoveryClient) {
        this.accountService = accountService;
        this.itemService = itemService;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/{accountName}")
    public ResponseEntity<Account> getAccountByName(@PathVariable String accountName) throws EntityNotFoundException {
        Account account = accountService.getAccountByName(accountName);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(path = {"/create"}, params = {"accountName"})
    public void createAccount(@RequestParam String accountName) throws EntityExistsException {
        //create account
        logger.info("creating account... " + accountName);
        accountService.createAccount(accountName);
    }
    @PostMapping("/save")
    public void saveChanges(Principal principal) throws EntityNotFoundException {
        Account account = accountService.getAccountByName(principal.getName());
        accountService.saveChanges(account);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(account.getName(), account.getLastSeen()));
    }
    @PostMapping("/save/{accountName}")
    public void saveChanges(@PathVariable String accountName) throws EntityNotFoundException {
        Account account = accountService.getAccountByName(accountName);
        accountService.saveChanges(account);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(account.getName(), account.getLastSeen()));
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountName) throws EntityNotFoundException {
        accountService.deleteAccountByName(accountName);
        String message = "\"%s's\" account was deleted successfully!".formatted(accountName);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //Item methods
    @GetMapping("/item/{accountName}")
    public ResponseEntity<List<Item>> createItem(@PathVariable String accountName) throws EntityNotFoundException {
        Account account = accountService.getAccountByName(accountName);
        List<Item> itemList = itemService.getItemsByAccount(account);
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }
    @PostMapping("/item")
    public ResponseEntity<Item> createItem(@RequestBody ItemDTO itemDTO) throws EntityNotFoundException {
        Item item = itemService.createItem(itemDTO);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @DeleteMapping("/item/{accountName}")
    public ResponseEntity<String> deleteItem(@PathVariable String accountName, String title) throws EntityNotFoundException {
        itemService.deleteItem(accountName, title);
        String message = "\"%1$s\" item was deleted successfully from \"%2$s's\" account !".formatted(title, accountName);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/item/{title}")
    public ResponseEntity<Item> editItem(@RequestBody ItemDTO itemDTO, @PathVariable String title ) throws EntityNotFoundException {
        Item item = itemService.updateItem(title, itemDTO);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
