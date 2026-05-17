package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.UserDTO;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 12) String password,
        Set<String> roles) {

}
