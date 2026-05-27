package com.esports.tournament_service.dto;

import lombok.Data;

@Data
public class TorneoResponseDTO {
    private Long id;
    private String nombre;
    private Long juegoId;
    private String fechaInicio;
    private String fechaFin;
    private String fechaCierreInscripcion;
    private Integer cupoMaximo;
    private String estado;
    private String modalidad;
}