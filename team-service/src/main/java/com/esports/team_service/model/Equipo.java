// Equipo.java
package com.esports.team_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    // ID del jugador que es capitán (viene de user-service)
    @Column(nullable = false)
    private Long capitanId;

    // ID del juego principal (viene de game-service)
    @Column(nullable = false)
    private Long juegoPrincipalId;

    @Column(nullable = false)
    private Boolean estado = true;
}