package com.esports.registration_service.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class InscripcionResponseDTO {

    private Long id;
    private Long torneoId;
    private Long equipoId;
    private Long jugadorId;
    private String tipoParticipante;
    private String estado;
    private LocalDate fechaInscripcion;
}