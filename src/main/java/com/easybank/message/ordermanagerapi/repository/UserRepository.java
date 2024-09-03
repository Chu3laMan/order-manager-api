package com.easybank.message.ordermanagerapi.repository;

import com.easybank.message.ordermanagerapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
