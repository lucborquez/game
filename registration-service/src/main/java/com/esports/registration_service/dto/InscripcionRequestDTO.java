package com.esports.registration_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InscripcionRequestDTO {

    @NotNull(message = "El torneoId es obligatorio")
    private Long torneoId;

    // Solo uno de los dos es obligatorio
    private Long equipoId;
    private Long jugadorId;

    @NotBlank(message = "El tipo de participante es obligatorio")
    @Pattern(regexp = "EQUIPO|JUGADOR",
            message = "El tipo debe ser EQUIPO o JUGADOR")
    private String tipoParticipante;
}