package com.esports.manager.registration.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InscripcionDTO {
    //identificador del torneo al que se inscribe
    @NotNull(message = "el torneo no puede ser nulo")
    private Long torneo;

    // Identificador del equipo inscrito
    private Long equipoId;

    // Identificador del jugador inscrito
    private Long jugadorId;

    // Tipo de participante: EQUIPO o JUGADOR
    @NotBlank(message = "El tipo de participante no puede estar vacío")
    private String tipoParticipante;
}
