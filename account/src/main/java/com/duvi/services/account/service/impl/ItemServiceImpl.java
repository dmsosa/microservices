package com.duvi.services.account.service.impl;

import com.duvi.services.account.client.AuthClient;
import com.duvi.services.account.client.StatClient;
import com.duvi.services.account.model.Account;
import com.duvi.services.account.model.Item;
import com.duvi.services.account.model.dto.ItemDTO;
import com.duvi.services.account.repository.AccountRepository;
import com.duvi.services.account.repository.ItemRepository;
import com.duvi.services.account.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private AccountRepository accountRepository;

    public ItemServiceImpl(ItemRepository itemRepository, AccountRepository accountRepository) {
        this.itemRepository = itemRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public List<ItemDTO> createDTOForAll(Iterable<Item> items) {
        List<ItemDTO> dtoList = new ArrayList<ItemDTO>();
        for (Item item : items) {
            ItemDTO itemDTO = createDTO(item);
            dtoList.add(itemDTO);
        }
        return dtoList;
    }

    @Override
    public ItemDTO createDTO(Item item) {
        return new ItemDTO(item.getAccount().getName(),
                item.getTitle(), item.getIcon(),
                item.getAmount(), item.getCategory(),
                item.getCurrency(), item.getFrequency(),
                item.getType());
    }

    @Override
    public Item editOrCreateByTitle(String title, ItemDTO itemDTO) {
        Item itemToReturn;
        Optional<Item> itemToUpdate = itemRepository.findByTitle(title);
        if (itemToUpdate.isEmpty()) {
            itemToReturn = new Item(itemDTO);
            Account account = accountRepository.findByName(itemDTO.accountName()).get();
            itemToReturn.setAccount(account);
            return itemRepository.save(itemToReturn);
        } else {
            itemToReturn = itemToUpdate.get();
            itemToReturn.setTitle(itemDTO.title());
            itemToReturn.setIcon(itemDTO.icon());
            itemToReturn.setAmount(itemDTO.amount());
            itemToReturn.setCurrency(itemDTO.currency());
            itemToReturn.setCategory(itemDTO.category());
            itemToReturn.setType(itemDTO.type());
            itemToReturn.setFrequency(itemDTO.frequency());
            return itemRepository.save(itemToReturn);
        }
    }

    @Override
    public Set<Item> editOrCreateAll(Iterable<ItemDTO> itemIterable) {
        Set<Item> itemSet = new HashSet<Item>();
        for (ItemDTO itemToUpdate : itemIterable) {
            Item updatedItem = this.editOrCreateByTitle(itemToUpdate.title(), itemToUpdate);
            itemSet.add(updatedItem);
        }
        return itemSet;
    }
}
