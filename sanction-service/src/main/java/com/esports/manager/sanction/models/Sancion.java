package com.esports.manager.sanction.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "sancion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sancion {
    //identificador unico AUTOGENERADO para cada sancion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sancion_id")
    private long sancionId;

    @Column(name = "usuario_id")
    private long usuarioId;

    @Column(name = "equipo_id")
    private long equipoId;

    @NotBlank(message = "el motivo no puede estar vacio")
    @Column(nullable = false)
    private String motivo;

    @NotNull(message = "la fecha de inicio no puede estar vacia")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "la fecha final no puede estar vacia")
    @Column(name = "fecha_final", nullable = false)
    private LocalDate  fechaFinal;

    // true = activa | false = cerrada
    @Column(nullable = false)
    private Boolean estado = true;

    @NotBlank(message = "la severidad no puede estar vacia")
    @Column(nullable = false)
    private String severidad;

}
