package com.esports.registration_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SancionClient {

    private static final Logger log = LoggerFactory.getLogger(SancionClient.class);

    @Value("${sanction.service.url}")
    private String sanctionServiceUrl;

    private final RestTemplate restTemplate;

    public SancionClient() {
        this.restTemplate = new RestTemplate();
    }

    // Pregunta al sanction-service si el usuario tiene sanción activa
    public boolean tieneSancionActiva(Long usuarioId) {
        try {
            String url = sanctionServiceUrl + "/api/sanciones/usuario/"
                    + usuarioId + "/activa";
            Boolean resultado = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(resultado);
        } catch (Exception e) {
            log.error("Error consultando sanction-service para usuarioId {}: {}",
                    usuarioId, e.getMessage());
            return false;
        }
    }
}