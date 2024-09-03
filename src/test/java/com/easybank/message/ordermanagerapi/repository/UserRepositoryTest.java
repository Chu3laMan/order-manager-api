package com.easybank.message.ordermanagerapi.repository;

import com.easybank.message.ordermanagerapi.entity.User;
import com.easybank.message.ordermanagerapi.integration.AbstractContainerBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        User user = new User();
        user.setName("Houssam El Mansouri");
        user.setEmail("elmansouri.houssam@gmail.com");

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User();
        user1.setName("Houssam El Mansouri");
        user1.setEmail("elmansouri.houssam@gmail.com");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Jane Doe");
        user2.setEmail("elmansouri.houssam@gmail.com");
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

}
