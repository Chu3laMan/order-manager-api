package com.easybank.message.ordermanagerapi.service;

import com.easybank.message.ordermanagerapi.entity.User;
import com.easybank.message.ordermanagerapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.find(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        user.setName("Updated Name");
        User updatedUser = userService.update(user);

        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

}
