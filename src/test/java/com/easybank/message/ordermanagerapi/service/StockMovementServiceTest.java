package com.easybank.message.ordermanagerapi.service;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.entity.StockMovement;
import com.easybank.message.ordermanagerapi.repository.StockMovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockMovementServiceTest {

    @Mock
    StockMovementRepository stockMovementRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private StockMovementService stockMovementService;

    private StockMovement stockMovement;
    private Item item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        item = new Item();
        item.setId(1L);
        item.setName("Item 1");

        stockMovement = new StockMovement();
        stockMovement.setId(1L);
        stockMovement.setItem(item);
        stockMovement.setQuantity(100);
    }

    @Test
    void testCreateStockMovement() {
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(stockMovement);

        StockMovement createdStockMovement = stockMovementService.create(stockMovement);

        assertNotNull(createdStockMovement);
        assertEquals(100, createdStockMovement.getQuantity());

        verify(stockMovementRepository, times(1)).save(stockMovement);
        verify(orderService, times(1)).applyStockMovement(stockMovement);
    }

    @Test
    void testFindStockMovementById() {
        when(stockMovementRepository.findById(1L)).thenReturn(Optional.of(stockMovement));

        StockMovement foundStockMovement = stockMovementService.find(1L);

        assertNotNull(foundStockMovement);
        assertEquals(1L, foundStockMovement.getId());

        verify(stockMovementRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateStockMovement() {
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(stockMovement);

        stockMovement.setQuantity(200);
        StockMovement updatedStockMovement = stockMovementService.update(stockMovement);

        assertNotNull(updatedStockMovement);
        assertEquals(200, updatedStockMovement.getQuantity());

        verify(stockMovementRepository, times(1)).save(stockMovement);
    }

    @Test
    void testDeleteStockMovement() {
        doNothing().when(stockMovementRepository).deleteById(1L);

        stockMovementService.delete(1L);

        verify(stockMovementRepository, times(1)).deleteById(1L);
    }

}
