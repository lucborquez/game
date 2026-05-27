package com.esports.team_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EquipoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El capitán es obligatorio")
    private Long capitanId;

    @NotNull(message = "El juego principal es obligatorio")
    private Long juegoPrincipalId;
}