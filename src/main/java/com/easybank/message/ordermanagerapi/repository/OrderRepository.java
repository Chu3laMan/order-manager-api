package com.easybank.message.ordermanagerapi.repository;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByItemAndIsCompletedFalse(Item item);
}
