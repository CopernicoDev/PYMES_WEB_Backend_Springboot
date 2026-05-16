package com.pymes.backend.nicolas.pymes_web_backend_springboot.dto.ServiceDTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServiceRequestDto(
                @NotBlank String servicename,

                @NotBlank String description,

                @NotNull @Positive BigDecimal price,

                @NotBlank String category,

                @NotNull @Positive Integer duration,

                @NotNull Long ownerId

) {

}
