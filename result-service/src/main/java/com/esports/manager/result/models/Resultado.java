package com.esports.manager.result.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultado_id")
    private Long ResultadoId;

    @NotNull(message = "la partida no puede ser nula")
    @Column(name = "partida_id", nullable = false)
    private Long PartidaId;

    @NotNull(message = "el ganador no puede ser nulo")
    @Column(name = "ganador_id", nullable = false)
    private Long ganadorId;

    @Min(value = 0, message = "el puntaje puntaje no puede ser negativo")
    @Column(name = "puntaje_a" , nullable = false)
    private Integer puntajeA;

    @Min(value = 0, message = "el puntaje puntaje no puede ser negativo")
    @Column(name = "puntaje_b" , nullable = false)
    private Integer puntajeB;

    @Column(name = "estado_validacion", nullable = false)
    private String estadoValidacion = "PENDIENTE";

    // Fecha en que se registró el resultado
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // true = activo | false = anulado
    @Column(nullable = false)
    private Boolean estado = true;
}
