package com.easybank.message.ordermanagerapi.controller;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.entity.StockMovement;
import com.easybank.message.ordermanagerapi.repository.ItemRepository;
import com.easybank.message.ordermanagerapi.repository.StockMovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.easybank.message.ordermanagerapi.integration.AbstractContainerBaseTest;

@SpringBootTest
@AutoConfigureMockMvc
public class StockMovementControllerIntegrationTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private ItemRepository itemRepository;

    private StockMovement stockMovement;
    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setName("Item 1");
        item = itemRepository.save(item);

        stockMovement = new StockMovement();
        stockMovement.setItem(item);
        stockMovement.setQuantity(100);
        stockMovement = stockMovementRepository.save(stockMovement);
    }

    @Test
    void testGetAllStockMovements() throws Exception {
        mockMvc.perform(get("/stockmovements")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(stockMovement.getId()));
    }

    @Test
    void testCreateStockMovement() throws Exception {
        String stockMovementJson = String.format(
                "{\"item\":{\"id\":%d},\"quantity\":50}",
                item.getId());

        mockMvc.perform(post("/stockmovements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockMovementJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.quantity").value(50));
    }

    @Test
    void testUpdateStockMovement() throws Exception {
        String stockMovementJson = String.format(
                "{\"item\":{\"id\":%d},\"quantity\":200}",
                item.getId());

        mockMvc.perform(put("/stockmovements/" + stockMovement.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockMovementJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(200));
    }

    @Test
    void testDeleteStockMovement() throws Exception {
        mockMvc.perform(delete("/stockmovements/" + stockMovement.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stockmovements/" + stockMovement.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
