package com.esports.registration_service.client;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TorneoClient {

    private static final Logger log = LoggerFactory.getLogger(TorneoClient.class);

    @Value("${tournament.service.url}")
    private String tournamentServiceUrl;

    private final RestTemplate restTemplate;

    public TorneoClient() {
        this.restTemplate = new RestTemplate();
    }

    // Verifica si el torneo existe y está abierto, y retorna su cupo máximo
    public TorneoInfoDTO obtenerTorneo(Long torneoId) {
        try {
            String url = tournamentServiceUrl + "/api/torneos/" + torneoId;
            return restTemplate.getForObject(url, TorneoInfoDTO.class);
        } catch (Exception e) {
            log.error("Error consultando tournament-service para torneoId {}: {}",
                    torneoId, e.getMessage());
            return null;
        }
    }

    // DTO interno para recibir solo lo que necesitamos del torneo
    @Data
    public static class TorneoInfoDTO {
        private Long id;
        private String estado;
        private Integer cupoMaximo;
        private String fechaCierreInscripcion;
    }
}