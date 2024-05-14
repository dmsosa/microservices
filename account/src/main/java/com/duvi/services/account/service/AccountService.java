package com.duvi.services.account.service;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.User;
import com.duvi.services.account.domain.dto.AccountDTO;
import com.duvi.services.account.domain.exception.EntityNotFoundException;
import com.duvi.services.account.domain.exception.EntityExistsException;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    AccountDTO createDTO(Account account);
    AccountDTO createAccount(String accountName) throws EntityExistsException ;
    AccountDTO editAccount(String name, Account account);
    AccountDTO getAccountByName(String name) throws EntityNotFoundException;
    void deleteAccountByName(String name) throws EntityNotFoundException;
    void saveChanges(AccountDTO account);

}
