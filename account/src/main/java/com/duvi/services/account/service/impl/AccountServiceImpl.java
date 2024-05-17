package com.duvi.services.account.service.impl;

import com.duvi.services.account.client.AuthClient;
import com.duvi.services.account.client.StatClient;
import com.duvi.services.account.model.*;
import com.duvi.services.account.model.dto.AccountDTO;
import com.duvi.services.account.model.exception.EntityNotFoundException;
import com.duvi.services.account.model.exception.EntityExistsException;
import com.duvi.services.account.repository.AccountRepository;
import com.duvi.services.account.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return new AccountDTO(account.getName(), account.getLastSeen(), account.getNote(), account.getIcon(), incomes, expenses);
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
    public AccountDTO editAccount(String name, AccountDTO accountDTO) {

        //updating user
        User oldUser = authClient.getUser(name);
        oldUser.setUsername(accountDTO.name());
        authClient.editUser(name, oldUser);

        //updating account
        accountRepository.deleteById(name);

        Account updatedAccount = new Account();
        updatedAccount.setName(accountDTO.name());
        updatedAccount.setLastSeen(LocalDateTime.now());
        updatedAccount.setNote(accountDTO.note());
        updatedAccount.setIcon(accountDTO.icon());

        Set<Item> itemSet = Stream.concat(accountDTO.incomes().stream(), accountDTO.expenses().stream()).collect(Collectors.toSet());
        updatedAccount.setItems(itemSet);

        return this.createDTO(accountRepository.save(updatedAccount));
    }
    @Override
    public AccountDTO editItems(String name, AccountDTO accountDTO) {

        //updating account
        Account account = accountRepository.findById(name).get();

        Set<Item> itemSet = Stream.concat(accountDTO.incomes().stream(), accountDTO.expenses().stream()).collect(Collectors.toSet());
        account.setItems(itemSet);
        account.setLastSeen(LocalDateTime.now());

        return this.createDTO(accountRepository.save(account));
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
