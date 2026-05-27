package com.esports.ranking_service.dto;

import lombok.Data;

@Data
public class RankingResponseDTO {
    private Long id;
    private Long torneoId;
    private Long participanteId;
    private Integer puntos;
    private Integer victorias;
    private Integer derrotas;
    private Integer diferencia;
    private Integer posicion;
}
