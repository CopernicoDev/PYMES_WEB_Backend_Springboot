package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllerstest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers.RoleController;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockmvc;

    // Using MockitoBean like in UserControllerTest
    @MockitoBean
    private RoleService roleService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testfindAllRoles() throws Exception {
        // Given
        Role r1 = new Role();
        r1.setRolname("ROLE_ADMIN");
        r1.setId(1L);

        Role r2 = new Role();
        r2.setRolname("ROLE_USER");
        r2.setId(2L);
        List<Role> roles = List.of(r1, r2);

        when(roleService.findAll()).thenReturn(roles);

        // When, Then
        mockmvc.perform(get("/api/roles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(roles.size()))
                .andExpect(jsonPath("$[0].rolname").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$[1].rolname").value("ROLE_USER"));

        verify(roleService).findAll();
        assertFalse(roles.isEmpty());
        assertEquals(2, roles.size());
        assertEquals("ROLE_ADMIN", roles.get(0).getRolname());
    }

    @Test
    void testfindById() throws Exception {
        // Given
        Role r1 = new Role();
        r1.setRolname("ROLE_ADMIN");
        r1.setId(1L);

        when(roleService.findById(1L)).thenReturn(Optional.of(r1));

        // When
        mockmvc.perform(get("/api/roles/1").contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rolname").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$.id").value(1L));

        verify(roleService).findById(1L);
    }

    @Test
    void testguardar() throws Exception {
        // Given
        Role r1 = new Role();
        r1.setRolname("ROLE_ADMIN");

        when(roleService.save(any(Role.class))).then(invocation -> {
            Role r = invocation.getArgument(0);
            r.setId(1L);
            return r;
        });

        // When
        mockmvc.perform(post("/api/roles").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(r1)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.rolname").value("ROLE_ADMIN"));

        verify(roleService).save(any(Role.class));
    }

    @Test
    void testdeleteById() throws Exception {
        // Given
        Role r1 = new Role();
        r1.setRolname("ROLE_ADMIN");
        r1.setId(1L);

        when(roleService.findById(1L)).thenReturn(Optional.of(r1));
        doNothing().when(roleService).deleteById(1L);

        // When, Then
        mockmvc.perform(delete("/api/roles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rolname").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$.id").value(1L));

        verify(roleService).findById(1L);
        verify(roleService).deleteById(1L);
    }

    @Test
    void testdeleteByIdNotFound() throws Exception {
        // Given
        when(roleService.findById(99L)).thenReturn(Optional.empty());

        // When, Then
        mockmvc.perform(delete("/api/roles/99").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(roleService).findById(99L);
        verify(roleService, never()).deleteById(anyLong());
    }
}
