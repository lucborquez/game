package com.esports.manager.ranking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

// Entidad que representa el ranking de un participante en un torneo
@Entity
@Table(name = "ranking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ranking {

    // Identificador único autogenerado para cada registro de ranking
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_id")
    private Long rankingId;

    // Identificador del torneo al que pertenece el ranking
    @NotNull(message = "El torneo no puede ser nulo")
    @Column(name = "torneo_id", nullable = false)
    private Long torneoId;

    // Identificador del participante en el ranking
    @NotNull(message = "El participante no puede ser nulo")
    @Column(name = "participante_id", nullable = false)
    private Long participanteId;

    // Puntos acumulados por el participante
    @Column(nullable = false)
    private Integer puntos = 0;

    // Cantidad de victorias del participante
    @Column(nullable = false)
    private Integer victorias = 0;

    // Cantidad de derrotas del participante
    @Column(nullable = false)
    private Integer derrotas = 0;

    // Diferencia de puntaje del participante
    @Column(nullable = false)
    private Integer diferencia = 0;

    // Posición actual en el ranking
    @Column(nullable = false)
    private Integer posicion = 0;
}