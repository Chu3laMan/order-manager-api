package com.easybank.message.ordermanagerapi.service;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        item = new Item();
        item.setId(1L);
        item.setName("Item 1");
    }

    @Test
    void testCreateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item createdItem = itemService.create(item);

        assertNotNull(createdItem);
        assertEquals("Item 1", createdItem.getName());

        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testFindItemById() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item foundItem = itemService.find(1L);

        assertNotNull(foundItem);
        assertEquals(1L, foundItem.getId());

        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        item.setName("Updated Item");
        Item updatedItem = itemService.update(item);

        assertNotNull(updatedItem);
        assertEquals("Updated Item", updatedItem.getName());

        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testDeleteItem() {
        doNothing().when(itemRepository).deleteById(1L);

        itemService.delete(1L);

        verify(itemRepository, times(1)).deleteById(1L);
    }

}
