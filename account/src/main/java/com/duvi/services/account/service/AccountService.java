package com.duvi.services.account.service;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.User;
import com.duvi.services.account.domain.exception.EntityNotFoundException;
import com.duvi.services.account.domain.exception.EntityExistsException;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    void createAccount(String accountName) throws EntityExistsException ;
    Account editAccount(String name, Account account);
    Account getAccountByName(String name) throws EntityNotFoundException;
    void deleteAccountByName(String name) throws EntityNotFoundException;
    void saveChanges(Account account);

}
