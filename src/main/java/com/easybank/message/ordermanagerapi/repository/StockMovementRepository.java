package com.easybank.message.ordermanagerapi.repository;

import com.easybank.message.ordermanagerapi.entity.Item;
import com.easybank.message.ordermanagerapi.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    @Query("SELECT SUM(sm.quantity) FROM StockMovement sm WHERE sm.item = :item")
    Long findTotalQuantityByItem(Item item);
}
