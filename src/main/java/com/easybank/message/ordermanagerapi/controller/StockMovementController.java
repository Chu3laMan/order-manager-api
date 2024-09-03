package com.easybank.message.ordermanagerapi.controller;

import com.easybank.message.ordermanagerapi.entity.StockMovement;
import com.easybank.message.ordermanagerapi.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stockmovements")
public class StockMovementController {

    StockMovementService stockMovementService;

    @Autowired
    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    public StockMovement createStockMovement(@RequestBody StockMovement stockMovement) {
        return stockMovementService.create(stockMovement);
    }

    @GetMapping
    public List<StockMovement> getAllStockMovements() {
        return stockMovementService.findAll();
    }

    @GetMapping("/{id}")
    public StockMovement getStockMovement(@PathVariable Long id) {
        return stockMovementService.find(id);
    }

    @PutMapping("/{id}")
    public StockMovement updateStockMovement(@PathVariable Long id, @RequestBody StockMovement stockMovement) {
        stockMovement.setId(id);
        return stockMovementService.update(stockMovement);
    }

    @DeleteMapping("/{id}")
    public void deleteStockMovement(@PathVariable Long id) {
        stockMovementService.delete(id);
    }

}
