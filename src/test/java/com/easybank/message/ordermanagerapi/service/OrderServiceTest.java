package com.easybank.message.ordermanagerapi.service;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.entity.Order;
import com.easybank.message.ordermanagerapi.entity.User;
import com.easybank.message.ordermanagerapi.repository.OrderRepository;
import com.easybank.message.ordermanagerapi.repository.StockMovementRepository;
import com.easybank.message.ordermanagerapi.service.OrderService;
import com.easybank.message.ordermanagerapi.util.EmailUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StockMovementRepository stockMovementRepository;

    @Mock
    private EmailUtil emailUtil;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private Item item;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        item = new Item();
        item.setId(1L);
        item.setName("Item 1");

        order = new Order();
        order.setId(1L);
        order.setCreationDate(LocalDateTime.now());
        order.setItem(item);
        order.setQuantity(10);
        order.setUser(user);
        order.setCompleted(false);
    }

    @Test
    void testCreateOrder_SufficientStock() {
        when(stockMovementRepository.findTotalQuantityByItem(item)).thenReturn(20L);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order createdOrder = orderService.create(order);
        assertTrue(createdOrder.isCompleted());
        verify(emailUtil, times(1)).sendOrderCompletionEmail(user, order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCreateOrder_InsufficientStock() {
        when(stockMovementRepository.findTotalQuantityByItem(item)).thenReturn(5L);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order createdOrder = orderService.create(order);
        assertFalse(createdOrder.isCompleted());
        verify(emailUtil, times(0)).sendOrderCompletionEmail(user, order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testFindOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order foundOrder = orderService.find(1L);
        assertNotNull(foundOrder);
        assertEquals(1L, foundOrder.getId());
    }

}
