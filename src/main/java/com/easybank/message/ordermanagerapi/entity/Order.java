package com.easybank.message.ordermanagerapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate;

    @ManyToOne
    private Item item;

    private int quantity;

    @ManyToOne
    private User user;

    private boolean isCompleted;

}
