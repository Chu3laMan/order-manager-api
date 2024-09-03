package com.easybank.message.ordermanagerapi.service;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.entity.Order;
import com.easybank.message.ordermanagerapi.entity.StockMovement;
import com.easybank.message.ordermanagerapi.repository.OrderRepository;
import com.easybank.message.ordermanagerapi.repository.StockMovementRepository;
import com.easybank.message.ordermanagerapi.util.EmailUtil;
import com.easybank.message.ordermanagerapi.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    OrderRepository orderRepository;
    StockMovementRepository stockMovementRepository;

    EmailUtil emailUtil;

    @Autowired
    public OrderService(OrderRepository orderRepository, StockMovementRepository stockMovementRepository, EmailUtil emailUtil) {
        this.orderRepository = orderRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.emailUtil = emailUtil;
    }

    public Order create(Order order) {
        order.setCreationDate(LocalDateTime.now());
        order.setCompleted(false);
        fulfillOrder(order);
        return orderRepository.save(order);
    }

    public Order find(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order update(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    private void fulfillOrder(Order order) {
        Item item = order.getItem();
        int quantity = order.getQuantity();


        Long availableStock = stockMovementRepository.findTotalQuantityByItem(item);

        if (availableStock != null && availableStock >= quantity) {

            order.setCompleted(true);
            LogUtil.logOrderCompleted(order);

            emailUtil.sendOrderCompletionEmail(order.getUser(), order);
        }
    }

    public void applyStockMovement(StockMovement stockMovement) {

        List<Order> pendingOrders = orderRepository.findByItemAndIsCompletedFalse(stockMovement.getItem());

        for (Order order : pendingOrders) {
            fulfillOrder(order);
            if (order.isCompleted()) {
                break;
            }
        }
    }
}
