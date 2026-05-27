package com.esports.manager.registration.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

//representa la inscripcion de un jugador o equipo a un torneo
@Entity
@Table(name = "inscripcion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Inscripcion {
    //identificador unico autogenerado para cada inscripcion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inscripcion_id")
    private Long inscripcionId;

    //identificador del torneo al que se inscribe
    @NotNull(message = "el torneo no puede estar vacio")
    @Column(name = "torneo_id", nullable = false)
    private Long torneoId;

    // Identificador del equipo inscrito
    @Column(name = "equipo_id")
    private Long equipoId;

    //identificador del equipo inscrito
    @Column(name = "jugador_id")
    private Long jugadorId;

    //tipo de participante: EQUIPO o JUGADOR
    @NotBlank(message = "el tipo de participante no puede estar vacio")
    @Column(name = "tipo_participante", nullable = false)
    private String tipoParticipante;

    //fecha en que se realizo la inscripcion
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    //true = activa | false = cancelada
    @Column(nullable = false)
    private Boolean estado = true;

}
