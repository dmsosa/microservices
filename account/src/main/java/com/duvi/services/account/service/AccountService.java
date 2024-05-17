package com.duvi.services.account.service;

import com.duvi.services.account.model.Account;
import com.duvi.services.account.model.dto.AccountDTO;
import com.duvi.services.account.model.exception.EntityNotFoundException;
import com.duvi.services.account.model.exception.EntityExistsException;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    AccountDTO createDTO(Account account);
    AccountDTO createAccount(String accountName) throws EntityExistsException ;
    AccountDTO editAccount(String name, AccountDTO accountDTO);
    AccountDTO editItems(String name, AccountDTO accountDTO);
    AccountDTO getAccountByName(String name) throws EntityNotFoundException;
    void deleteAccountByName(String name) throws EntityNotFoundException;
    void saveChanges(AccountDTO account);

}
