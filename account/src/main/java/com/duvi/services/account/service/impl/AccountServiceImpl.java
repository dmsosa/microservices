package com.duvi.services.account.service.impl;

import com.duvi.services.account.client.AuthClient;
import com.duvi.services.account.client.StatClient;
import com.duvi.services.account.domain.*;
import com.duvi.services.account.domain.dto.AccountDTO;
import com.duvi.services.account.domain.exception.EntityNotFoundException;
import com.duvi.services.account.domain.exception.EntityExistsException;
import com.duvi.services.account.repository.AccountRepository;
import com.duvi.services.account.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private AuthClient authClient;
    private StatClient statClient;
    public AccountServiceImpl(AccountRepository accountRepository,
                              AuthClient authClient,
                              StatClient statClient) {
        this.authClient = authClient;
        this.statClient = statClient;
        this.accountRepository = accountRepository;
    }

    //the DTO organizes the incomes and expenses before sending them to the stats service or any other client
    @Override
    public AccountDTO createDTO(Account account) {
        List<Item> incomes = new ArrayList<Item>();
        List<Item> expenses = new ArrayList<Item>();
        for (Item item : account.getItems()) {
            if (item.getType() == Type.INCOME) {
                incomes.add(item);
            } else if (item.getType() == Type.EXPENSE) {
                expenses.add(item);
            } else {
                continue;
            }
        }
        return new AccountDTO(account.getName(), account.getLastSeen(), account.getNote(), incomes, expenses);
    }
    @Override
    public AccountDTO createAccount(String accountName) throws EntityExistsException {
        if (accountRepository.existsByName(accountName)) {
            throw new EntityExistsException("Account with name: \"%s\" does not exists!".formatted(accountName));
        }

        Account account = new Account();
        account.setName(accountName);
        account.setLastSeen(LocalDateTime.now());
        account.setNote("I'm using microservices!");
        return createDTO(accountRepository.save(account));
    }

    @Override
    public AccountDTO editAccount(String name, Account account) {
        Account oldAccount = accountRepository.findById(name).get();
        oldAccount.setLastSeen(LocalDateTime.now());
        oldAccount.setItems(account.getItems());
        oldAccount.setNote(account.getNote());
        AccountDTO dto = this.createDTO(account);
        statClient.saveAccount(dto);
        return dto;
    }

    @Override
    public AccountDTO getAccountByName(String name) throws EntityNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(name);
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with name: \"%s\" does not exists!".formatted(name));
        }
        return this.createDTO(optionalAccount.get());
    }

    @Override
    public void deleteAccountByName(String name) throws EntityNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(name);
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with name: \"%s\" does not exists!".formatted(name));
        }
        authClient.deleteUser(name);
        accountRepository.deleteById(name);
    }

    @Override
    public void saveChanges(AccountDTO account) {

        //todo accountDTO + itemDTO
        statClient.saveAccount(account);
    }
}
