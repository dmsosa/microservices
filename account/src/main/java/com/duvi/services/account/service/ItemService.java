package com.duvi.services.account.service;

import com.duvi.services.account.model.Item;
import com.duvi.services.account.model.dto.ItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ItemService {
    public List<ItemDTO> createDTOForAll(Iterable<Item> items);
    public ItemDTO createDTO(Item item);
    public Item editOrCreateByTitle(String title, ItemDTO itemDTO);

    public Set<Item> editOrCreateAll(Iterable<ItemDTO> itemIterable);
}
