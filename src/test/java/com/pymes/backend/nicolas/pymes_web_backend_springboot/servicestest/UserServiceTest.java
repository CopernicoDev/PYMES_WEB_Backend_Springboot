package com.pymes.backend.nicolas.pymes_web_backend_springboot.servicestest;

import org.springframework.beans.factory.annotation.Autowired;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.UserRepository;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.UserService;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    UserRepository userRepository;

    UserService userService;

    @BeforeEach
    void setUp() {

        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

    }

    @Test
    void testfindAllUsers() {
        // Given - usuario existente
        User u1 = new User();
        u1.setUsername("Nicolás");
        u1.setId(1L);

        User u2 = new User();
        u2.setUsername("Yaiza");
        u2.setId(2L);
        java.util.List<User> users = List.of(u1, u2);

        // When
        when(userRepository.findAllUsers()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Nicolás", result.get(0).getUsername());
        verify(userRepository).findAllUsers();

    }

    @Test
    void testfindById() {
        // Given - usuario existente
        User u1 = new User();
        u1.setUsername("Nicolás");
        u1.setId(1L);

        User u2 = new User();
        u2.setUsername("Yaiza");
        u2.setId(2L);
        java.util.List<User> users = List.of(u1, u2);

        // When
        when(userRepository.findById(1L)).thenReturn(Optional.of(u1));

        Optional<User> result = userService.findById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Nicolás", result.get().getUsername());
        verify(userRepository).findById(1L);

    }

    @Test
    void testFindByIdNotFound() {
        // When - usuario no existente
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> res = userService.findById(99L);

        // Then
        assertTrue(res.isEmpty());
        verify(userRepository).findById(99L);
    }

    @Test
    void testSave() {
        // Given - usuario existente
        User toSave = new User();
        toSave.setUsername("Nuevo");

        // When
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User arg = invocation.getArgument(0);
            arg.setId(5L);
            return arg;
        });

        User saved = userService.save(toSave);

        // Then
        assertNotNull(saved);
        assertEquals(5L, saved.getId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testDeleteById() {
        // When
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteById(1L);

        // Then
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteAll() {
        // When
        doNothing().when(userRepository).deleteAll();

        userService.deleteAll();

        // Then
        verify(userRepository).deleteAll();
    }

}
