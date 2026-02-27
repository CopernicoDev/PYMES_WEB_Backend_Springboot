package com.pymes.backend.nicolas.pymes_web_backend_springboot.servicestest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.RoleRepository;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.repositories.UserRepository;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleService;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleServiceImpl;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.UserServiceImpl;

public class RoleServiceTest {

    RoleService roleService;
    RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void testFindByRolName() {
        // Given - rol existente
        Role r1 = new Role();
        r1.setRolname("admin");
        r1.setId(1L);

        Role r2 = new Role();
        r2.setRolname("user");
        r2.setId(2L);
        java.util.List<Role> roles = List.of(r1, r2);

        // When
        when(roleRepository.findByRolname("admin")).thenReturn(Optional.of(r1));
        when(roleRepository.findByRolname("user")).thenReturn(Optional.of(r2));

        Optional<Role> result = roleService.findByRolname("admin");
        Optional<Role> result2 = roleService.findByRolname("user");

        // Then
        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getRolname());
        verify(roleRepository).findByRolname("admin");

        assertTrue(result2.isPresent());
        assertEquals("user", result2.get().getRolname());
        verify(roleRepository).findByRolname("user");

    }

    @Test
    void testFindById() {
        // Given - rol existente
        Role r1 = new Role();
        r1.setRolname("admin");
        r1.setId(1L);

        Role r2 = new Role();
        r2.setRolname("user");
        r2.setId(2L);
        java.util.List<Role> roles = List.of(r1, r2);

        // When
        when(roleRepository.findById(1L)).thenReturn(Optional.of(r1));
        when(roleRepository.findById(2L)).thenReturn(Optional.of(r2));

        Optional<Role> result = roleService.findById(1L);
        Optional<Role> result2 = roleService.findById(2L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getRolname());
        verify(roleRepository).findById(1L);

        assertTrue(result2.isPresent());
        assertEquals("user", result2.get().getRolname());
        verify(roleRepository).findById(2L);

    }

}
