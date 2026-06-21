package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.ServiceDTO;

// --- Importación estándar de Java ---
import java.math.BigDecimal;

// --- Importación de nuestra entidad ---
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service;

/**
 * DTO de SALIDA (Response) para devolver datos de un servicio al frontend.
 * 
 * ¿Por qué no devolver la entidad Service directamente?
 * - La entidad Service tiene un campo "owner" que es un objeto User completo
 *   (con contraseña incluida). El DTO solo expone ownerId y ownerUsername.
 * - Evitamos problemas de serialización con FetchType.LAZY (LazyInitializationException).
 * - Controlamos exactamente qué datos ve el frontend.
 * 
 * Ejemplo de JSON que el frontend recibe:
 * {
 *   "id": 1,
 *   "servicename": "Instalación de Firewall",
 *   "description": "Configuración completa de firewall empresarial",
 *   "price": 299.99,
 *   "category": "Seguridad",
 *   "duration": 120,
 *   "active": true,
 *   "ownerId": 1,
 *   "ownerUsername": "nicolas"
 * }
 */
public record ServiceResponseDto(
        Long id,
        String servicename,
        String description,
        BigDecimal price,
        String category,
        Integer duration,
        Boolean active,
        Long ownerId,
        String ownerUsername) {

    /**
     * Método de fábrica que convierte una entidad Service a DTO.
     * 
     * Se protege contra servicios sin dueño asignado (owner == null)
     * usando el operador ternario: si owner es null, ownerId y ownerUsername
     * serán null en vez de lanzar NullPointerException.
     * 
     * Este método se usa en el Controller:
     * ServiceResponseDto.fromEntity(servicio) → convierte y devuelve al frontend.
     */
    // Método para convertir una entidad Service en un DTO ServiceResponseDto
    public static ServiceResponseDto fromEntity(Service service) {

        // Protegemos el código por si acaso el servicio no tiene un dueño asignado
        Long ownerId = service.getOwner() != null ? service.getOwner().getId() : null;
        String ownerUsername = service.getOwner() != null ? service.getOwner().getUsername() : null;
        return new ServiceResponseDto(
                service.getId(),
                service.getServicename(),
                service.getDescription(),
                service.getPrice(),
                service.getCategory(),
                service.getDuration(),
                service.getActive(),
                ownerId,
                ownerUsername);
    }
}
