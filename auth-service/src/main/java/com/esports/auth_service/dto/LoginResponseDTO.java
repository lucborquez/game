package com.esports.auth_service.dto;
//Lo que se devuelve cuando el login es exitoso

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;
    private String email;
    private String rol;

    public LoginResponseDTO(String token, String email, String rol) {
        this.token = token;
        this.email = email;
        this.rol = rol;
    }
}
