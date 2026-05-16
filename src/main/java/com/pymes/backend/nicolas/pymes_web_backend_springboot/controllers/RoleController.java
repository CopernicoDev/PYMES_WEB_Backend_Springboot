package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.findById(id);
        if (roleOptional.isPresent()) {
            return ResponseEntity.ok(roleOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Role> save(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.save(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.findById(id); // Verificar si el rol existe
        if (roleOptional.isPresent()) {
            roleService.deleteById(id); // Eliminar el rol usando el ID
            return ResponseEntity.ok(roleOptional.get()); // Retornar el rol eliminado
        } else {
            return ResponseEntity.notFound().build(); // Retornar 404 si no se encuentra
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        roleService.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
