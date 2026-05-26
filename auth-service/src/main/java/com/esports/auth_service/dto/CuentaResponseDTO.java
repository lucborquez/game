package com.esports.auth_service.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class CuentaResponseDTO {

    private Long id;
    private String email;
    private String rol;
    private Boolean estado;
    private LocalDate fechaCreacion;
}
