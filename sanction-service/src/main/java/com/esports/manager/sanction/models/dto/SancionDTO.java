package com.esports.manager.sanction.models.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
//objetos de transferencia de datos para CREAR O ACTUALIZAR una sancion
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SancionDTO {
    private Long usuarioId;
    private Long equipoId;
    //motivo de sancion
    @NotBlank(message = "el motivo no puede estar vacio")
    private String motivo;
    //fecha de inicio
    @NotNull(message = "la fecha de inicio no puede ser nula")
    private LocalDate fechaInicio;
    //fecha de fin
    @NotNull(message = "la fecha de fin no puede ser nula")
    private LocalDate fechaFin;
    //nivel de gravdedad de la sancion
    @NotBlank(message =  "la severidad no puede estar vacia")
    private String severidad;
}
