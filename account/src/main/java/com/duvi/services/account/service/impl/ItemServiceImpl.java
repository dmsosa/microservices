package com.duvi.services.account.service.impl;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import com.duvi.services.account.domain.dto.ItemDTO;
import com.duvi.services.account.repository.AccountRepository;
import com.duvi.services.account.repository.ItemRepository;
import com.duvi.services.account.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private AccountRepository accountRepository;
    private ItemRepository itemRepository;
    public ItemServiceImpl(AccountRepository accountRepository, ItemRepository itemRepository) {
        this.accountRepository = accountRepository;
        this.itemRepository = itemRepository;
    }
    @Override
    public Item createItem(ItemDTO itemDTO) {
        Item item = new Item();
        Account account = accountRepository.findById(itemDTO.accountName()).get();
        item.setAccount(account);
        item.setTitle(itemDTO.title());
        item.setIcon(itemDTO.icon());
        item.setAmount(itemDTO.amount());
        item.setCurrency(itemDTO.currency());
//        item.setFrequency(itemDTO.frequency());
        item.setType(itemDTO.type());

        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id).get();
        Account account = accountRepository.findById(itemDTO.accountName()).get();
        item.setAccount(account);
        item.setTitle(itemDTO.title());
        item.setAmount(itemDTO.amount());
        item.setIcon(itemDTO.icon());
        item.setCurrency(itemDTO.currency());
//        item.setFrequency(itemDTO.frequency());
        item.setType(itemDTO.type());
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new RuntimeException("Item does not exists");
        }
        itemRepository.delete(optionalItem.get());
    }

    @Override
    public Item getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new RuntimeException("Item does not exists");
        }
        return optionalItem.get();
    }
}
