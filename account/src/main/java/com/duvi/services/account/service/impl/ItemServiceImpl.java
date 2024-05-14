package com.duvi.services.account.service.impl;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import com.duvi.services.account.domain.dto.ItemDTO;
import com.duvi.services.account.domain.exception.EntityNotFoundException;
import com.duvi.services.account.repository.AccountRepository;
import com.duvi.services.account.repository.ItemRepository;
import com.duvi.services.account.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Item createItem(ItemDTO itemDTO) throws EntityNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(itemDTO.accountName());
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with name: \"%s\" does not exists!".formatted(itemDTO.accountName()));
        }
        Account account = optionalAccount.get();
        Item item = new Item();
        item.setAccount(account);
        item.setTitle(itemDTO.title());
        item.setIcon(itemDTO.icon());
        item.setAmount(itemDTO.amount());
        item.setCurrency(itemDTO.currency());
        item.setFrequency(itemDTO.frequency());
        item.setType(itemDTO.type());
        item.setCategory(itemDTO.category());

        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(String title, ItemDTO itemDTO) throws EntityNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(itemDTO.accountName());
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with name: \"%s\" does not exists!".formatted(itemDTO.accountName()));
        }
        Item item = itemRepository.findByTitle(title).get();
        Account account = optionalAccount.get();
        item.setAccount(account);
        item.setTitle(itemDTO.title());
        item.setAmount(itemDTO.amount());
        item.setIcon(itemDTO.icon());
        item.setCurrency(itemDTO.currency());
        item.setFrequency(itemDTO.frequency());
        item.setType(itemDTO.type());
        item.setCategory(itemDTO.category());
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(String accountName, String title) throws EntityNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(accountName);
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with name: \"%s\" does not exists!".formatted(accountName));
        }
        List<Item> itemList = itemRepository.findByAccount(optionalAccount.get());
        Item item = itemList.stream().filter(it -> it.getTitle() == title).toList().getFirst();
        if (item == null) {
            throw new EntityNotFoundException("Item with title \"%s\" does not exists".formatted(title));
        }
        itemRepository.delete(item);
    }


    @Override
    public List<Item> getItemsByAccountName(String accountName) throws EntityNotFoundException {
        Optional<Account> optional = accountRepository.findById(accountName);
        if (optional.isPresent()) {
            return itemRepository.findByAccount(optional.get());
        } else {
            throw new EntityNotFoundException("Account with name: \"%s\" does not exists!".formatted(accountName));
        }

    }
}
