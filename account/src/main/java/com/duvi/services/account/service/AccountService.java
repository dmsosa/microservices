package com.duvi.services.account.service;

import com.duvi.services.account.domain.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    Account createAccount();
    Account updateAccount();
    Account getAccountByName(String name);
    Account deleteAccountByName(String name);
}
