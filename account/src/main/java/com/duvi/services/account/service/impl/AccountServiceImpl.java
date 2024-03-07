package com.duvi.services.account.service.impl;

import com.duvi.services.account.client.AuthClient;
import com.duvi.services.account.client.StatClient;
import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import com.duvi.services.account.domain.User;
import com.duvi.services.account.domain.dto.ItemDTO;
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
    public Account createAccount(User user) {
        if (accountRepository.existsByName(user.getUsername())) {
            throw new RuntimeException("User Already Exists"); //todo exception
        }

        authClient.createUser(user);
        Account account = new Account();
        account.setName(user.getUsername());
        account.setLastSeen(LocalDateTime.now());
        account.setNote("I'm using microservices!");
        accountRepository.save(account);

        return accountRepository.findById(user.getUsername()).get();

    }

    @Override
    public Account saveChanges(String name, Account account) {
        Account oldAccount = accountRepository.findById(name).get();
        oldAccount.setLastSeen(LocalDateTime.now());
        oldAccount.setIncomes(account.getIncomes());
        oldAccount.setExpenses(account.getExpenses());
        oldAccount.setNote(account.getNote());
        statClient.saveAccount(account);
        return accountRepository.findById(account.getName()).get();
    }

    @Override
    public Account getAccountByName(String name) {
        Optional<Account> optionalAccount = accountRepository.findById(name);
        if (optionalAccount.isEmpty()) {
            throw new RuntimeException("Account does not exists!");
        }
        return optionalAccount.get();
    }

    @Override
    public void deleteAccountByName(String name) {
        Optional<Account> optionalAccount = accountRepository.findById(name);
        if (optionalAccount.isEmpty()) {
            throw new RuntimeException("Account does not exists!");
        }
        accountRepository.deleteById(name);
    }

    @Override
    public Item createItem(ItemDTO itemDTO) {
        return null;
    }
}
