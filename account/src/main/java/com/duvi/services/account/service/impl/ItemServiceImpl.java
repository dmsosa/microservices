package com.duvi.services.account.service.impl;


import com.duvi.services.account.model.Account;
import com.duvi.services.account.model.Item;
import com.duvi.services.account.model.dto.ItemDTO;
import com.duvi.services.account.repository.AccountRepository;
import com.duvi.services.account.repository.ItemRepository;
import com.duvi.services.account.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        return new ItemDTO(item.getId(), item.getAccount().getName(),
                item.getTitle(), item.getIcon(),
                item.getAmount(), item.getCategory(),
                item.getCurrency(), item.getFrequency(),
                item.getType());
    }

    @Override
    public Item editOrCreateById(Long idToUpdate, ItemDTO itemDTO) {
        Item itemToReturn;

        if (idToUpdate != null) {
            itemToReturn = itemRepository.findById(idToUpdate).get();
            itemToReturn.setTitle(itemDTO.title());
            itemToReturn.setIcon(itemDTO.icon());
            itemToReturn.setAmount(itemDTO.amount());
            itemToReturn.setCurrency(itemDTO.currency());
            itemToReturn.setCategory(itemDTO.category());
            itemToReturn.setType(itemDTO.type());
            itemToReturn.setFrequency(itemDTO.frequency());
        } else {
            itemToReturn = new Item(itemDTO);
            Account account = accountRepository.findByName(itemDTO.accountName()).get();
            itemToReturn.setAccount(account);
        }
        return itemRepository.save(itemToReturn);
    }

    @Override
    public Set<Item> editOrCreateAll(Iterable<ItemDTO> itemIterable) {
        Set<Item> itemSet = new HashSet<Item>();
        for (ItemDTO itemToUpdate : itemIterable) {
            Item updatedItem = this.editOrCreateById(itemToUpdate.id(), itemToUpdate);
            itemSet.add(updatedItem);
        }
        return itemSet;
    }
    @Override
    public void compareAndDeleteItems(Account accountOldItems, Set<Item> itemsToSave) {

        Set<Item> oldItems = accountOldItems.getItems();
        Set<Item> clone = new HashSet<>(oldItems);
        List<Long> idToPersist = itemsToSave.stream().map(Item::getId).toList();

        for (Item oldItem : clone) {
            if (!idToPersist.contains(oldItem.getId())) {
                oldItems.remove(oldItem);
                itemRepository.delete(oldItem);
            }
        }
    }

}
