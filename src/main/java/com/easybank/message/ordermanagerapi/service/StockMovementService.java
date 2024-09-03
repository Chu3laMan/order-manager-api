package com.easybank.message.ordermanagerapi.service;

import com.easybank.message.ordermanagerapi.entity.StockMovement;
import com.easybank.message.ordermanagerapi.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockMovementService {

    StockMovementRepository stockMovementRepository;
    OrderService orderService;

    @Autowired
    public StockMovementService(StockMovementRepository stockMovementRepository, OrderService orderService) {
        this.stockMovementRepository = stockMovementRepository;
        this.orderService = orderService;
    }

    public StockMovement create(StockMovement stockMovement) {
        stockMovement.setCreationDate(LocalDateTime.now());
        stockMovement = stockMovementRepository.save(stockMovement);

        orderService.applyStockMovement(stockMovement);

        return stockMovement;
    }

    public StockMovement find(Long id) {
        return stockMovementRepository.findById(id).orElse(null);
    }

    public List<StockMovement> findAll() {
        return stockMovementRepository.findAll();
    }

    public StockMovement update(StockMovement stockMovement) {
        return stockMovementRepository.save(stockMovement);
    }

    public void delete(Long id) {
        stockMovementRepository.deleteById(id);
    }
}
