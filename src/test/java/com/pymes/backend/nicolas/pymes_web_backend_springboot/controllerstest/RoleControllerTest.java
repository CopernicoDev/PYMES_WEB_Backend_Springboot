package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllerstest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers.RoleController;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleService;

import tools.jackson.databind.ObjectMapper;

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
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoleService roleService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

    }

    @Test
    void testFindById() throws Exception {
        // Given
        Role role = new Role();
        role.setId(1L);
        role.setRolname("Admin");

        when(roleService.findById(1L)).thenReturn(Optional.of(role));

        // When
        mockMvc.perform(get("/api/roles/search/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.rolname").value("Admin"));

        // Then
        verify(roleService).findById(1L);
        assertEquals(1L, role.getId());
        assertEquals("Admin", role.getRolname());
        assertNotNull(role);
        assertNotNull(role.getId());
        assertNotNull(role.getRolname());
    }

}
