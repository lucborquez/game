package com.esports.manager.match.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

//entidades que "representa" una aprtida dentro de un torneo
@Entity
@Table(name = "partida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Partida {

    //identificador unico autogenerado para cada partida
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partida_id")
    private Long patidaId;

    //identificador del torneo de partida
    @NotNull(message = "El torneo no puede ser nulo")
    @Column(name = "torneo_id", nullable = false)
    private Long torneoId;

    //identificador del primer participante
    @NotNull(message = "El participante A no puede ser nulo")
    @Column(name = "participante_a_id",nullable = false)
    private Long participanteAId;

    //idetificador del segundo participante
    @NotNull(message = "El participante B no puede ser nulo")
    @Column(name = "participante_b_id", nullable = false)
    private Long participanteBId;

    //ronda del torneo ne que se juega la partida
    @NotBlank(message = "la ronda no puede estar vacia")
    @Column(nullable = false)
    private String ronda;

    //fecha y hora programda para la partida
    @Column(name = "fecha")
    private LocalDateTime fecha;

    //true = activa | false = cancelado
    @Column(nullable = false)
    private Boolean estado = true;
}
