package com.easybank.message.ordermanagerapi.controller;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.entity.Order;
import com.easybank.message.ordermanagerapi.entity.User;
import com.easybank.message.ordermanagerapi.integration.AbstractContainerBaseTest;
import com.easybank.message.ordermanagerapi.repository.ItemRepository;
import com.easybank.message.ordermanagerapi.repository.OrderRepository;
import com.easybank.message.ordermanagerapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Item item;
    private Order order;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user = userRepository.save(user);

        item = new Item();
        item.setName("Item 1");
        item = itemRepository.save(item);

        order = new Order();
        order.setCreationDate(LocalDateTime.now());
        order.setItem(item);
        order.setQuantity(10);
        order.setUser(user);
        order.setCompleted(false);
        order = orderRepository.save(order);
    }

    @Test
    void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order.getId()));
    }

    @Test
    void testCreateOrder() throws Exception {
        String orderJson = String.format(
                "{\"creationDate\":\"%s\",\"item\":{\"id\":%d},\"quantity\":10,\"user\":{\"id\":%d},\"completed\":false}",
                new Date(), item.getId(), user.getId());

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.item.id").value(item.getId()));
    }
}
