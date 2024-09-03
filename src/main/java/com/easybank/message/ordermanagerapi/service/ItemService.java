package com.easybank.message.ordermanagerapi.service;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item find(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item update(Item item) {
        return itemRepository.save(item);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

}
