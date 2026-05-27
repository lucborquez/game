package com.esports.resultservice.model.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoRequestDTO {

    @NotNull(message = "el ID de la partida es obligatorio")
    private Long partidaId;

    private Long ganadorId;

    @NotNull(message = "El puntaje A es obligatorio")
    @Min(value = 0, message = "Los puntajes no pueden ser negativos")
    private Integer puntajeA;

    @NotNull(message = "El puntaje B es obligatorio")
    @Min(value = 0, message = "Los puntajes no pueden ser negativos")
    private Integer puntajeB;

    private String evidenciaUrl;
}
