package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.UserDTO.UserRequestDto;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.UserDTO.UserResponseDto;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.RoleService;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(UserResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> view(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(UserResponseDto.fromEntity(userOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto userDto) {
        // 1. Convertimos el DTO que llega a una Entidad User
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        // Si el frontend envía "ROLE_ADMIN" o "ROLE_USER"
        if (userDto.roles() != null) {
            for (String rolName : userDto.roles()) {
                // 1. Buscamos el rol real en la base de datos
                Optional<Role> roleOpt = roleService.findByRolname(rolName);

                // 2. Si existe en la BD, lo añadimos al usuario
                if (roleOpt.isPresent()) {
                    user.addRole(roleOpt.get());
                }
            }
        }

        // 2. Guardamos la entidad en la base de datos
        User savedUser = userService.save(user);

        // 3. Convertimos la Entidad guardada a DTO para responder de forma segura
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDto.fromEntity(savedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            userService.deleteById(id);
            // Devolvemos el usuario borrado en formato DTO
            return ResponseEntity.ok(UserResponseDto.fromEntity(userOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        userService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}