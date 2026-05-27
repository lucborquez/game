package com.esports.match_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PartidaRequestDTO {

    @NotNull(message = "El torneoId es obligatorio")
    private Long torneoId;

    @NotNull(message = "El participanteAId es obligatorio")
    private Long participanteAId;

    @NotNull(message = "El participanteBId es obligatorio")
    private Long participanteBId;

    @NotBlank(message = "La ronda es obligatoria")
    private String ronda;

    @NotBlank(message = "La fecha y hora es obligatoria")
    private String fechaHora;

}