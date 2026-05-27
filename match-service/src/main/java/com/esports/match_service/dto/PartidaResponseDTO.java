package com.esports.match_service.dto;

import lombok.Data;

@Data
public class PartidaResponseDTO {

    private Long id;
    private Long torneoId;
    private Long participanteAId;
    private Long participanteBId;
    private String ronda;
    private String fechaHora;
    private String estado;
}