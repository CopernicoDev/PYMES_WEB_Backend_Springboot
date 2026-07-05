package com.pymes.backend.nicolas.pymes_web_backend_springboot.controllers;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.LoginDTO.LoginResponseDto;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.LoginDTO.LoginRequestDto;

@RestController
@RequestMapping("/api/auth")
public class Authcontroller {

    private final UserService userService;

    public Authcontroller(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST /api/auth/login
     * Valida email y password (en texto plano por ahora).
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginDto) {

        Optional<User> userOptional = userService.findByEmail(loginDto.email());

        // Si el correo no existe
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas.");
        }

        User user = userOptional.get();

        // Si la contraseña no coincide
        if (!user.getPassword().equals(loginDto.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas.");
        }

        // Login exitoso
        LoginResponseDto response = new LoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail());

        return ResponseEntity.ok(response);
    }
}
