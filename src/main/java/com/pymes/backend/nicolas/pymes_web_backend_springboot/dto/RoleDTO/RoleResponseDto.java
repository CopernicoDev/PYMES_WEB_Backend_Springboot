package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.RoleDTO;

import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;

public record RoleResponseDto(
        Long id,
        String rolname) {
    public static RoleResponseDto fromEntity(Role role) {
        return new RoleResponseDto(
                role.getId(),
                role.getRolname());
    }
}
