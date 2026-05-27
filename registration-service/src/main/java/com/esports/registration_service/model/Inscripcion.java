package com.esports.registration_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del torneo (viene de tournament-service)
    @Column(nullable = false)
    private Long torneoId;

    // ID del equipo (puede ser null si participa un jugador solo)
    private Long equipoId;

    // ID del jugador (puede ser null si participa un equipo)
    private Long jugadorId;

    // EQUIPO o JUGADOR
    @Column(nullable = false)
    private String tipoParticipante;

    /*
     Estados:
     PENDIENTE  → recién creada
     ACEPTADA   → confirmada
     CANCELADA  → cancelada
    */
    @Column(nullable = false)
    private String estado = "PENDIENTE";

    @Column(nullable = false)
    private LocalDate fechaInscripcion;
}