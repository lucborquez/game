package com.esports.manager.ranking.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

// Objeto de transferencia de datos para crear o actualizar un ranking
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RankingDTO {

    // Identificador del torneo al que pertenece el ranking
    @NotNull(message = "El torneo no puede ser nulo")
    private Long torneoId;

    // Identificador del participante en el ranking
    @NotNull(message = "El participante no puede ser nulo")
    private Long participanteId;

    // Puntos acumulados por el participante
    private Integer puntos = 0;

    // Cantidad de victorias del participante
    private Integer victorias = 0;

    // Cantidad de derrotas del participante
    private Integer derrotas = 0;

    // Diferencia de puntaje del participante
    private Integer diferencia = 0;

    // Posición actual en el ranking
    private Integer posicion = 0;
}