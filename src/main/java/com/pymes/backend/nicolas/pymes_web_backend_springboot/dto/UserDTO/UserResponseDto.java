package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.UserDTO;

// --- Importaciones estándar de Java ---
import java.util.Set;
import java.util.stream.Collectors;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.User;

/**
 * DTO de SALIDA (Response) para devolver datos de usuario al frontend.
 * 
 * ¿Por qué no devolver la entidad User directamente?
 * - Seguridad: la entidad User contiene la CONTRASEÑA. Si la devuelves
 *   en el JSON, cualquier cliente la vería. El DTO la EXCLUYE.
 * - Control: elegimos exactamente qué campos ve el frontend.
 * - Desacoplamiento: si cambias la entidad (añades campos internos),
 *   el API del frontend no se rompe.
 * 
 * Este DTO incluye: id, username, email y los NOMBRES de los roles
 * (no los objetos Role completos).
 * 
 * Ejemplo de JSON que el frontend recibe:
 * {
 *   "id": 1,
 *   "username": "nicolas",
 *   "email": "nicolas@ejemplo.com",
 *   "roles": ["ROLE_ADMIN", "ROLE_USER"]
 * }
 */
public record UserResponseDto(
                Long id,
                String username,
                String email,
                Set<String> roles) {

        /**
         * Método de fábrica (factory method) que convierte una entidad User a DTO.
         * 
         * Flujo:
         * 1. user.getRoles() → obtiene el Set<Role> (objetos completos)
         * 2. .stream() → lo convierte en un flujo para transformarlo
         * 3. .map(role -> role.getRolname()) → de cada Role extrae solo el nombre
         * 4. .collect(Collectors.toSet()) → agrupa los nombres en un Set<String>
         * 
         * Así convertimos Set<Role> → Set<String> (solo los nombres).
         * Ejemplo: [Role{id=1, rolname="ROLE_ADMIN"}] → ["ROLE_ADMIN"]
         */
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
