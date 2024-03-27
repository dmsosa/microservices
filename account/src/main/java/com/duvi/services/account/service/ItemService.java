package com.duvi.services.account.service;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import com.duvi.services.account.domain.dto.ItemDTO;
import com.duvi.services.account.domain.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    Item createItem(ItemDTO itemDTO) throws EntityNotFoundException;
    Item updateItem(String title, ItemDTO itemDTO) throws EntityNotFoundException;
    void deleteItem(String accountName, String title) throws EntityNotFoundException;
    List<Item> getItemsByAccount(Account account);
}
