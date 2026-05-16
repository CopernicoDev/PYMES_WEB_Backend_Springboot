package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.ServiceDTO;

import java.math.BigDecimal;
import com.pymes.backend.nicolas.pymes_web_backend_springboot.models.Service;

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
