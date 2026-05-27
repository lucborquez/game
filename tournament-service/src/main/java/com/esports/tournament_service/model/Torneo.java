package com.esports.tournament_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "torneos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    // ID del juego (viene de game-service)
    @Column(nullable = false)
    private Long juegoId;

    // Formato: "2024-06-01"
    @Column(nullable = false)
    private String fechaInicio;

    @Column(nullable = false)
    private String fechaFin;

    // Fecha límite para inscribirse
    @Column(nullable = false)
    private String fechaCierreInscripcion;

    @Column(nullable = false)
    private Integer cupoMaximo;

    /*
     Estados posibles:
     BORRADOR   → recién creado, no visible
     ABIERTO    → aceptando inscripciones
     CERRADO    → inscripciones cerradas
     EN_CURSO   → torneo jugándose
     FINALIZADO → terminado
     CANCELADO  → cancelado
    */
    @Column(nullable = false)
    private String estado = "BORRADOR";

    // Ej: "5v5", "1v1", "Battle Royale"
    @Column(nullable = false)
    private String modalidad;
}