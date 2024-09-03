package com.easybank.message.ordermanagerapi.repository;

import com.easybank.message.ordermanagerapi.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
