package com.easybank.message.ordermanagerapi.repository;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.integration.AbstractContainerBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void testSaveAndFindItem() {
        Item item = new Item();
        item.setName("Test Item");
        Item savedItem = itemRepository.save(item);
        assertNotNull(savedItem.getId());
        Item foundItem = itemRepository.findById(savedItem.getId()).orElse(null);
        assertNotNull(foundItem);
        assertEquals("Test Item", foundItem.getName());
    }

    @Test
    void testFindAllItems() {
        Item item1 = new Item();
        item1.setName("Item 1");
        itemRepository.save(item1);
        Item item2 = new Item();
        item2.setName("Item 2");
        itemRepository.save(item2);
        List<Item> items = itemRepository.findAll();
        assertEquals(2, items.size());
    }

}
