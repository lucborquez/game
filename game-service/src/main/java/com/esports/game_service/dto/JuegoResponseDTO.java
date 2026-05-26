package com.esports.game_service.dto;

import lombok.Data;

@Data
public class JuegoResponseDTO {

    private Long id;
    private String nombre;
    private String genero;
    private String modalidad;
    private Integer jugadoresPorEquipo;
    private boolean estado;
}
