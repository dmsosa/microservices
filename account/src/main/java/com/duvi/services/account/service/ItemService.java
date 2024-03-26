package com.duvi.services.account.service;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import com.duvi.services.account.domain.dto.ItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    Item createItem(ItemDTO itemDTO);
    Item updateItem(Long id, ItemDTO itemDTO);
    void deleteItem(Long id);
    Item getItemById(Long id);
    List<Item> getItemsByAccount(Account account);
}
