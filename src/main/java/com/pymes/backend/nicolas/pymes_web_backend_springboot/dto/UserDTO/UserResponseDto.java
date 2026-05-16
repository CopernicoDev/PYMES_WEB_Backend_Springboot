package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.UserDTO;

import java.util.Set;
import java.util.stream.Collectors;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;

public record UserResponseDto(
                Long id,
                String username,
                String email,
                Set<String> roles) {
        // conversion de User a UserResponseDto
        public static UserResponseDto fromEntity(User user) {
                Set<String> roleNames = user.getRoles().stream()
                                .map(role -> role.getRolname())
                                .collect(Collectors.toSet());
                return new UserResponseDto(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                roleNames);
        }

}
