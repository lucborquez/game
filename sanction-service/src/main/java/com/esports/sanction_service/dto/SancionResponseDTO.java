package com.esports.sanction_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SancionResponseDTO {
    private Long id;
    private Long usuarioId;
    private Long equipoId;
    private String motivo;
    private String severidad;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean estado;
}
