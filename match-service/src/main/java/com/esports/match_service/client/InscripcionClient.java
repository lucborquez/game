package com.esports.match_service.client;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class InscripcionClient {

    private static final Logger log = LoggerFactory.getLogger(InscripcionClient.class);

    @Value("${registration.service.url}")
    private String registrationServiceUrl;

    private final RestTemplate restTemplate;

    public InscripcionClient() {
        this.restTemplate = new RestTemplate();
    }

    // Verifica si un participante está inscrito en el torneo
    public boolean participanteInscrito(Long torneoId, Long participanteId) {
        try {
            String url = registrationServiceUrl
                    + "/api/inscripciones/torneo/" + torneoId;
            InscripcionDTO[] inscripciones = restTemplate
                    .getForObject(url, InscripcionDTO[].class);
            if (inscripciones == null) return false;

            return Arrays.stream(inscripciones).anyMatch(i ->
                    participanteId.equals(i.getJugadorId()) ||
                            participanteId.equals(i.getEquipoId()));
        } catch (Exception e) {
            log.error("Error consultando registration-service: {}", e.getMessage());
            return false;
        }
    }

    // DTO interno
    @Data
    public static class InscripcionDTO {
        private Long jugadorId;
        private Long equipoId;
        private String estado;

    }
}