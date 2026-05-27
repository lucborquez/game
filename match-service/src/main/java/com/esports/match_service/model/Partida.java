package com.esports.match_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partidas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long torneoId;

    // ID del primer participante (jugador o equipo)
    @Column(nullable = false)
    private Long participanteAId;

    // ID del segundo participante
    @Column(nullable = false)
    private Long participanteBId;

    // Ronda del torneo: "CUARTOS", "SEMIFINAL", "FINAL", etc.
    @Column(nullable = false)
    private String ronda;

    // Fecha y hora programada: "2024-06-15 18:00"
    @Column(nullable = false)
    private String fechaHora;

    /*
     Estados:
     PROGRAMADA → creada, esperando jugarse
     EN_CURSO   → jugándose ahora
     FINALIZADA → terminada
     CANCELADA  → cancelada
    */
    @Column(nullable = false)
    private String estado = "PROGRAMADA";
}