package com.esports.team_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class EquipoResponseDTO {
    private Long id;
    private String nombre;
    private Long capitanId;
    private Long juegoPrincipalId;
    private Boolean estado;
    private List<MiembroResponseDTO> miembros;
}