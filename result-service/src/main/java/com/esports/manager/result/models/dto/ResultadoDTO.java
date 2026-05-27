package com.esports.manager.result.models.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoDTO {

    @NotNull(message = "la partida no puede ser nula")
    private Long partidaId;

    @NotNull(message = "El ganador no puede ser nulo")
    private Long  ganadorId;

    @Min(value = 0, message = "el puntaje no puede ser negativo")
    private Integer puntajeA;

    @Min(value = 0, message = "el puntaje no puede ser negativo")
    private Integer puntajeB;
}
