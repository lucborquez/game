package com.esports.resultservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultados")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long partidaId;

    private Long ganadorId;

    @Column(nullable = false)
    private Integer puntajeA;

    @Column(nullable = false)
    private Integer puntajeB;

    @Column(nullable = false)
    private String estadoValidacion;

    private String evidenciaUrl;

    private String justificacionAnulacion;

    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        if (this.estadoValidacion == null) {
            this.estadoValidacion = "PENDIENTE";
        }
    }
}
