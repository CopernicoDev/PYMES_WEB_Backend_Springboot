package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.LoginDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank @Email String email,
        @NotBlank String password) {
}
