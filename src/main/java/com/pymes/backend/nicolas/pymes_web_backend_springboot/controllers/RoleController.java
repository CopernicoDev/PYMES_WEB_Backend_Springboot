package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.RoleDTO.RoleResponseDto;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleResponseDto> getAllRoles() {
        return roleService.findAll()
                .stream()
                .map(RoleResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> view(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.findById(id);
        if (roleOptional.isPresent()) {
            return ResponseEntity.ok(RoleResponseDto.fromEntity(roleOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> save(@RequestBody Role role) {
        Role savedRole = roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleResponseDto.fromEntity(savedRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleResponseDto> deleteById(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.findById(id);
        if (roleOptional.isPresent()) {
            roleService.deleteById(id);
            return ResponseEntity.ok(RoleResponseDto.fromEntity(roleOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        roleService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
