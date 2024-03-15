package com.duvi.services.account.service;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    Account createAccount(User user);
    Account editAccount(String name, Account account);
    Account getAccountByName(String name);
    void deleteAccountByName(String name);
    void saveChanges(Account account);

}
