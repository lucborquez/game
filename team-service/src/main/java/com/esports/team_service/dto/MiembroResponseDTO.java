package com.esports.team_service.dto;

import lombok.Data;

@Data
public class MiembroResponseDTO {
    private Long id;
    private Long usuarioId;
    private String rolDentroEquipo;
}