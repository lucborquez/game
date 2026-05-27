package com.esports.tournament_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TorneoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El juego es obligatorio")
    private Long juegoId;

    @NotBlank(message = "La fecha de inicio es obligatoria")
    private String fechaInicio;

    @NotBlank(message = "La fecha de fin es obligatoria")
    private String fechaFin;

    @NotBlank(message = "La fecha de cierre de inscripción es obligatoria")
    private String fechaCierreInscripcion;

    @NotNull(message = "El cupo máximo es obligatorio")
    @Positive(message = "El cupo máximo debe ser positivo")
    private Integer cupoMaximo;

    @NotBlank(message = "La modalidad es obligatoria")
    private String modalidad;
}