package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.ServiceDTO;

// --- Importación estándar de Java ---
import java.math.BigDecimal;

// --- Importaciones de validación (Jakarta Bean Validation) ---
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO de ENTRADA (Request) para crear un nuevo servicio IT.
 * 
 * Define los datos que el frontend debe enviar al hacer
 * POST /api/services para crear un servicio.
 * 
 * Las anotaciones de validación se activan con @Valid en el Controller.
 * Si alguna falla, Spring devuelve automáticamente un error 400 Bad Request
 * sin ejecutar el código del método.
 * 
 * Ejemplo de JSON que el frontend envía:
 * {
 *   "servicename": "Instalación de Firewall",
 *   "description": "Configuración completa de firewall empresarial",
 *   "price": 299.99,
 *   "category": "Seguridad",
 *   "duration": 120,
 *   "ownerId": 1
 * }
 */
public record ServiceRequestDto(

                // @NotBlank: no puede ser null, vacío ni solo espacios
                @NotBlank String servicename,

                // Descripción obligatoria del servicio
                @NotBlank String description,

                // @NotNull: no puede ser null (BigDecimal es un objeto, no un primitivo)
                // @Positive: debe ser mayor que 0 (no aceptamos servicios gratuitos o negativos)
                @NotNull @Positive BigDecimal price,

                // Categoría obligatoria (ej: "Seguridad", "Redes", "Cloud")
                @NotBlank String category,

                // Duración en minutos. @Positive asegura que sea > 0.
                @NotNull @Positive Integer duration,

                // ID del usuario dueño del servicio.
                // El Controller busca el User real en la BD usando este ID.
                // No enviamos el objeto User completo, solo su ID (más eficiente).
                @NotNull Long ownerId

) {

}
