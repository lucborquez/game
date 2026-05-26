package com.esports.user_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apodo es obligatorio")
    private String apodo;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene formato válido")
    private String email;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;

}
