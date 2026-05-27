package com.esports.sanction_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

// Entidad que representa una sancion aplicada a un usuario o equipo
@Entity
@Table(name = "sanciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del usuario sancionado (viene de user-service, puede ser null si se sanciona equipo)
    @Column(name = "usuario_id")
    private Long usuarioId;

    // ID del equipo sancionado (viene de team-service, puede ser null si se sanciona usuario)
    @Column(name = "equipo_id")
    private Long equipoId;

    // Motivo de la sancion
    @Column(nullable = false)
    private String motivo;

    // Nivel de gravedad: LEVE, MODERADA, GRAVE
    @Column(nullable = false)
    private String severidad;

    // Fecha de inicio de la sancion
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    // Fecha de fin de la sancion
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    // true = activa | false = cerrada/cumplida
    @Column(nullable = false)
    private Boolean estado = true;
}
