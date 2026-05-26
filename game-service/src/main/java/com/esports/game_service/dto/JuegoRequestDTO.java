package com.esports.game_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class JuegoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El genero es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
    private  String genero;

    @NotBlank(message = "La modalidad es obligatoria")
    @Size(min = 2, max = 15, message = "El nombre debe tener entre 2 y 15 caracteres")
    private String modalidad;

    @NotNull(message = "Los jugadores por equipo son obligatorios")
    @Positive(message = "Debe ser un numero positivo")
    private Integer jugadoresPorEquipo;

}
