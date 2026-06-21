package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.RoleDTO;

// --- Importación de nuestro modelo ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Role;

/**
 * DTO de SALIDA (Response) para devolver datos de un rol al frontend.
 * 
 * Los roles son simples (solo tienen id y rolname), pero igualmente
 * usamos un DTO para mantener la CONSISTENCIA del patrón en toda la API.
 * 
 * Además, si en el futuro la entidad Role añade campos internos
 * (ej: permissions, createdAt), el DTO protege al frontend de esos cambios.
 * 
 * Nota: no existe RoleRequestDto porque al crear un rol (POST /api/roles)
 * se envía directamente la entidad Role (solo tiene id y rolname).
 * En el futuro podrías crear uno si necesitas validaciones específicas.
 * 
 * Ejemplo de JSON que el frontend recibe:
 * {
 *   "id": 1,
 *   "rolname": "ROLE_ADMIN"
 * }
 */
public record RoleResponseDto(
        Long id,
        String rolname) {

    /**
     * Método de fábrica que convierte una entidad Role a DTO.
     * Extrae solo los campos que queremos exponer al frontend.
     */
    public static RoleResponseDto fromEntity(Role role) {
        return new RoleResponseDto(
                role.getId(),
                role.getRolname());
    }
}
