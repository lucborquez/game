package com.esports.tournament_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CambioEstadoDTO {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}