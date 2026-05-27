package com.esports.team_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "miembros_equipo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiembroEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long equipoId;

    // ID del jugador (viene de user-service)
    @Column(nullable = false)
    private Long usuarioId;

    // Ej: "CAPITAN", "JUGADOR", "SUPLENTE"
    @Column(nullable = false)
    private String rolDentroEquipo;
}