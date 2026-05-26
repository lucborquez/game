package com.esports.game_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "juegos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Juego {

    //Genera el ID automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nombre del juego
    @Column(nullable = false, unique = true)
    private String nombre;

    //Genero del juego
    @Column(nullable = false)
    private String genero;

    //Modalidad
    @Column(nullable = false)
    private String modalidad;

    //Jugadores por equipo
    @Column(nullable = false)
    private Integer jugadoresPorEquipo;

    //Estado del juego (true=Activo / false=Desactivado)
    @Column(nullable = false)
    private boolean estado = true;
}