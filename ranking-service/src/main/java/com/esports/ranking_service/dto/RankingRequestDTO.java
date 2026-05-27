package com.esports.ranking_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class RankingRequestDTO {

    @NotNull(message = "El torneoId es obligatorio")
    private Long torneoId;

    @NotNull(message = "El participanteId es obligatorio")
    private Long participanteId;

    @PositiveOrZero(message = "Los puntos no pueden ser negativos")
    private Integer puntos = 0;

    @PositiveOrZero(message = "Las victorias no pueden ser negativas")
    private Integer victorias = 0;

    @PositiveOrZero(message = "Las derrotas no pueden ser negativas")
    private Integer derrotas = 0;

    private Integer diferencia = 0;

    @PositiveOrZero(message = "La posicion no puede ser negativa")
    private Integer posicion = 0;
}
