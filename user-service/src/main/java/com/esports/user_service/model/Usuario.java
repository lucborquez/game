package com.esports.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    //Nombre de usuario unico
    @Column(nullable = false, unique = true)
    private String apodo;

    @Column(nullable = false ,unique = true)
    private String email;

    //Jugador, Organizador o Admin
    @Column(nullable = false)
    private String rol;

    //true=activo / false=desactivado
    @Column(nullable = false)
    private boolean estado = true;

    @Column(nullable = false)
    private String fechaRegistro;
}
