package com.esports.ranking_service.model;

import jakarta.persistence.*;
import lombok.*;

// Entidad que representa el ranking de un participante en un torneo
@Entity
@Table(name = "ranking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del torneo al que pertenece (viene de tournament-service)
    @Column(nullable = false)
    private Long torneoId;

    // ID del participante (equipo o jugador)
    @Column(nullable = false)
    private Long participanteId;

    // Puntos acumulados
    @Column(nullable = false)
    private Integer puntos = 0;

    // Cantidad de victorias
    @Column(nullable = false)
    private Integer victorias = 0;

    // Cantidad de derrotas
    @Column(nullable = false)
    private Integer derrotas = 0;

    // Diferencia de puntaje (puntajeA - puntajeB acumulado)
    @Column(nullable = false)
    private Integer diferencia = 0;

    // Posicion actual en el ranking (0 = sin calcular)
    @Column(nullable = false)
    private Integer posicion = 0;
}
