package com.esports.team_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UsuarioClient {

    private static final Logger log = LoggerFactory.getLogger(UsuarioClient.class);

    // Lee la URL de user-service del application.properties
    @Value("${user.service.url}")
    private String userServiceUrl;

    private final RestTemplate restTemplate;

    public UsuarioClient() {
        this.restTemplate = new RestTemplate();
    }

    // Consulta a user-service si el usuario existe y está activo
    public boolean usuarioExisteYEstaActivo(Long usuarioId) {
        try {
            String url = userServiceUrl + "/api/usuarios/" + usuarioId + "/activo";
            Boolean activo = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(activo);
        } catch (Exception e) {
            log.error("Error consultando user-service para usuarioId {}: {}", usuarioId, e.getMessage());
            return false;
        }
    }
}