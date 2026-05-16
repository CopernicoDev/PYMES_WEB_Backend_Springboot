package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllerstest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers.UserController;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockitoBean
    private UserService userService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

    }

    @Test
    void testfindAllUsers() throws Exception {
        // Given
        // Given - crear datos inline
        User u1 = new User();
        u1.setUsername("Nicolás");
        u1.setId(1L);

        User u2 = new User();
        u2.setUsername("Yaiza");
        u2.setId(2L);
        java.util.List<User> users = List.of(u1, u2);

        when(userService.findAllUsers()).thenReturn(users);

        // When , Then
        mockmvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].username").value("Nicolás"))
                .andExpect(jsonPath("$[1].username").value("Yaiza"));

        verify(userService).findAllUsers();
        assertFalse(users.isEmpty());
        assertEquals(2, users.size());
        assertEquals("Nicolás", users.get(0).getUsername());
    }

    @Test
    void testfindById() throws Exception {
        // Given
        // Given - crear datos inline
        User u1 = new User();
        u1.setUsername("Nicolás");
        u1.setId(1L);

        User u2 = new User();
        u2.setUsername("Yaiza");
        u2.setId(2L);

        when(userService.findById(1L)).thenReturn(Optional.of(u1));

        // When
        mockmvc.perform(get("/api/users/search/1").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Nicolás"))
                .andExpect(jsonPath("$.id").value(1L));

        verify(userService).findById(1L);
    }

    @Test
    void testguardar() throws Exception {
        // Given - crear datos inline
        User u1 = new User();
        u1.setUsername("Nicolás");
        u1.setId(1L);

        User u2 = new User();
        u2.setUsername("Yaiza");
        u2.setId(2L);

        // Given
        when(userService.save(any())).then(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        // When
        mockmvc.perform(post("/api/users/postuser").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(u1)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("Nicolás"));

        verify(userService).save(any());
        assertEquals("Nicolás", u1.getUsername());
        assertEquals(1L, u1.getId());
    }

    @Test
    void testdeleteById() throws Exception {
        // Given - usuario existente
        User u1 = new User();
        u1.setUsername("Nicolás");
        u1.setId(1L);

        when(userService.findById(1L)).thenReturn(Optional.of(u1));
        doNothing().when(userService).deleteById(1L);

        // When / Then - debe devolver 200 y el usuario eliminado
        mockmvc.perform(delete("/api/users/delete/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Nicolás"))
                .andExpect(jsonPath("$.id").value(1));

        verify(userService).findById(1L);
        verify(userService).deleteById(1L);
    }

    @Test
    void testdeleteByIdNotFound() throws Exception {
        // Given - usuario no existe
        when(userService.findById(99L)).thenReturn(Optional.empty());

        // When / Then - debe devolver 404
        mockmvc.perform(delete("/api/users/delete/99").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService).findById(99L);
        verify(userService, never()).deleteById(anyLong());
    }

    @Test
    void testdeleteAll() throws Exception {
        // Given
        // Given - usuario existente
        User u1 = new User();
        u1.setUsername("Nicolás");
        u1.setId(1L);

        User u2 = new User();
        u2.setUsername("Yaiza");
        u2.setId(2L);
        java.util.List<User> users = List.of(u1, u2);

        doNothing().when(userService).deleteAll();

        // When
        mockmvc.perform(delete("/api/users/delete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Then
        verify(userService).deleteAll();
    }

}
