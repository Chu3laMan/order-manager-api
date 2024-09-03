package com.easybank.message.ordermanagerapi.repository;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.entity.Order;
import com.easybank.message.ordermanagerapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.easybank.message.ordermanagerapi.integration.AbstractContainerBaseTest;

public class OrderRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void testSaveAndFindOrder() {
        User user = new User();
        user.setName("Houssam El Mansouri");
        user.setEmail("elmansouri.houssam@gmail.com");
        user = userRepository.save(user);

        Item item = new Item();
        item.setName("Test Item");
        item = itemRepository.save(item);

        Order order = new Order();
        order.setItem(item);
        order.setUser(user);
        order.setQuantity(10);

        Order savedOrder = orderRepository.save(order);
        assertNotNull(savedOrder.getId());

        Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        assertNotNull(foundOrder);
        assertEquals(10, foundOrder.getQuantity());
    }

    @Test
    void testFindByItemAndIsCompletedFalse() {
        User user = new User();
        user.setName("Houssam El Mansouri");
        user.setEmail("elmansouri.houssam@gmail.com");
        user = userRepository.save(user);

        Item item = new Item();
        item.setName("Test Item");
        item = itemRepository.save(item);

        Order order1 = new Order();
        order1.setItem(item);
        order1.setUser(user);
        order1.setQuantity(10);
        order1.setCompleted(false);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setItem(item);
        order2.setUser(user);
        order2.setQuantity(5);
        order2.setCompleted(true);
        orderRepository.save(order2);

        List<Order> pendingOrders = orderRepository.findByItemAndIsCompletedFalse(item);
        assertEquals(1, pendingOrders.size());
        assertEquals(10, pendingOrders.get(0).getQuantity());
    }

}
