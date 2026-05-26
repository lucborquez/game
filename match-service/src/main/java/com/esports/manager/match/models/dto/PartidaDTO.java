package com.esports.manager.match.models.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartidaDTO {

    @NotNull(message = "el torneo no puede estar vacio")
    private Long torneoId;

    @NotNull(message = "el participante A no puede ser nulo")
    private Long participanteAId;

    @NotNull(message = "el participante B no puede ser nulo")
    private Long participanteBId;
    @NotBlank(message = "La ronda no puede estar vacia")
    private String ronda;

    private LocalDateTime fecha;
}
