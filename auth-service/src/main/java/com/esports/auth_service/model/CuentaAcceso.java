package com.esports.auth_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cuentas_acceso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String rol;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(nullable = false)
    private LocalDate fechaCreacion;
}
