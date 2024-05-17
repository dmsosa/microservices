package com.duvi.services.account.controller;

import com.duvi.services.account.model.dto.AccountDTO;
import com.duvi.services.account.model.exception.EntityNotFoundException;
import com.duvi.services.account.model.exception.EntityExistsException;
import com.duvi.services.account.service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Save an account means to "make its statistics" from its current items
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
    public ResponseEntity<AccountDTO> getAccountByName(@PathVariable String accountName) throws EntityNotFoundException {
        AccountDTO account = accountService.getAccountByName(accountName);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(path = {"/create/{accountName}"})
    public ResponseEntity<AccountDTO> createAccount(@PathVariable String accountName) throws EntityExistsException {
        //create account
        logger.info("creating account... " + accountName);
        AccountDTO account = accountService.createAccount(accountName);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }
    @PostMapping("/edit/{accountName}")
    public ResponseEntity<AccountDTO> editAccount(@PathVariable String accountName, @RequestBody AccountDTO accountDTO) throws EntityNotFoundException {
        AccountDTO updatedAccount = accountService.editAccount(accountName, accountDTO);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
    @PostMapping("/save/{accountName}")
    public void saveChanges(@PathVariable String accountName) throws EntityNotFoundException {
        AccountDTO account = accountService.getAccountByName(accountName);
        accountService.saveChanges(account);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(account.name(), account.lastSeen()));
    }
    @DeleteMapping("/{accountName}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountName) throws EntityNotFoundException {
        accountService.deleteAccountByName(accountName);
        String message = "\"%s's\" account was deleted successfully!".formatted(accountName);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //Item methods
    @PostMapping("/items/{accountName}")
    public ResponseEntity<AccountDTO> updateItems(@PathVariable String accountName, @RequestBody AccountDTO accountDTO) throws EntityNotFoundException {
        AccountDTO account = accountService.editItems(accountName, accountDTO);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
