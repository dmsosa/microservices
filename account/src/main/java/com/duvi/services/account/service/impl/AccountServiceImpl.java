package com.duvi.services.account.service.impl;

import com.duvi.services.account.client.AuthClient;
import com.duvi.services.account.client.StatClient;
import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.User;
import com.duvi.services.account.domain.exception.EntityNotFoundException;
import com.duvi.services.account.domain.exception.EntityExistsException;
import com.duvi.services.account.repository.AccountRepository;
import com.duvi.services.account.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Override
    public Account createAccount(String accountName) throws EntityExistsException {
        if (accountRepository.existsByName(accountName)) {
            throw new RuntimeException("Account Already Exists"); //todo exception
        }

        Account account = new Account();
        account.setName(accountName);
        account.setLastSeen(LocalDateTime.now());
        account.setNote("I'm using microservices!");
        return accountRepository.save(account);
    }

    @Override
    public Account editAccount(String name, Account account) {
        Account oldAccount = accountRepository.findById(name).get();
        oldAccount.setLastSeen(LocalDateTime.now());
        oldAccount.setItems(account.getItems());
        oldAccount.setNote(account.getNote());
        statClient.saveAccount(account);
        return accountRepository.findById(account.getName()).get();
    }

    @Override
    public Account getAccountByName(String name) throws EntityNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(name);
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with name: \"%s\" does not exists!".formatted(name));
        }
        return optionalAccount.get();
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
    public void saveChanges(Account account) {

        //todo accountDTO + itemDTO
        statClient.saveAccount(account);
    }
}
