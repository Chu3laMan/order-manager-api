package com.easybank.message.ordermanagerapi.repository;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.entity.StockMovement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


import com.easybank.message.ordermanagerapi.integration.AbstractContainerBaseTest;

public class StockMovementRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    StockMovementRepository stockMovementRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    void testSaveAndFindStockMovement() {
        Item item = new Item();
        item.setName("Test Item");
        item = itemRepository.save(item);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setItem(item);
        stockMovement.setQuantity(100);

        StockMovement savedStockMovement = stockMovementRepository.save(stockMovement);
        assertNotNull(savedStockMovement.getId());
        StockMovement foundStockMovement = stockMovementRepository.findById(savedStockMovement.getId()).orElse(null);
        assertNotNull(foundStockMovement);
        assertEquals(100, foundStockMovement.getQuantity());
    }

    @Test
    void testFindTotalQuantityByItem() {
        Item item = new Item();
        item.setName("Test Item");
        item = itemRepository.save(item);

        StockMovement stockMovement1 = new StockMovement();
        stockMovement1.setItem(item);
        stockMovement1.setQuantity(100);
        stockMovementRepository.save(stockMovement1);

        StockMovement stockMovement2 = new StockMovement();
        stockMovement2.setItem(item);
        stockMovement2.setQuantity(50);
        stockMovementRepository.save(stockMovement2);

        Long totalQuantity = stockMovementRepository.findTotalQuantityByItem(item);
        assertEquals(150, totalQuantity);
    }

}
