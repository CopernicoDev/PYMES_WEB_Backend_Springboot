package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.UserDTO;

// --- Importaciones estándar de Java ---
import java.util.Set;

// --- Importaciones de validación (Jakarta Bean Validation) ---
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de ENTRADA (Request) para crear un nuevo usuario.
 * 
 * ¿Qué es un record?
 * Java Records (desde Java 14) son clases inmutables que generan
 * automáticamente: constructor, getters (sin prefijo "get"), equals(),
 * hashCode() y toString(). Ideales para DTOs porque son solo datos.
 * 
 * ¿Por qué un DTO de entrada?
 * - Separa lo que el frontend ENVÍA de lo que la BD necesita.
 * - Permite validar los datos ANTES de procesarlos.
 * - Evita exponer campos internos (ej: el frontend no debe poder
 *   asignar un ID directamente).
 * 
 * Las anotaciones de validación se activan con @Valid en el Controller.
 * Si alguna falla, Spring devuelve automáticamente un error 400 Bad Request.
 * 
 * Ejemplo de JSON que el frontend envía:
 * {
 *   "username": "nicolas",
 *   "email": "nicolas@ejemplo.com",
 *   "password": "123456",
 *   "roles": ["ROLE_ADMIN", "ROLE_USER"]
 * }
 */
public record UserRequestDto(

        // @NotBlank: no puede ser null, vacío (""), ni solo espacios ("   ")
        @NotBlank String username,

        // @NotBlank + @Email: debe ser un email válido (contener @ y dominio)
        @NotBlank @Email String email,

        // @NotBlank + @Size(min=6, max=12): la contraseña debe tener entre 6 y 12 caracteres
        @NotBlank @Size(min = 6, max = 12) String password,

        // Lista de nombres de roles a asignar. Puede ser null (usuario sin roles).
        // El Controller buscará cada rol por nombre en la BD antes de asignarlo.
        Set<String> roles) {

}
