package com.duvi.services.account.service;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import com.duvi.services.account.domain.User;
import com.duvi.services.account.domain.dto.ItemDTO;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    Account createAccount(User user);
    Account saveChanges(String name, Account account);
    Account getAccountByName(String name);
    void deleteAccountByName(String name);
    Item createItem(ItemDTO itemDTO);

}
