package com.esports.sanction_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SancionRequestDTO {

    // Al menos uno de los dos debe estar presente (usuario o equipo)
    private Long usuarioId;
    private Long equipoId;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotBlank(message = "La severidad es obligatoria (LEVE, MODERADA, GRAVE)")
    private String severidad;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;
}
