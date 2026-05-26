package com.esports.user_service.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String apodo;
    private String email;
    private String rol;
    private Boolean estado;
    private LocalDate fechaRegistro;
}
