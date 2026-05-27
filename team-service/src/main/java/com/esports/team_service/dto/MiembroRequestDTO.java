package com.esports.team_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MiembroRequestDTO {

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotBlank(message = "El rol dentro del equipo es obligatorio")
    private String rolDentroEquipo;
}