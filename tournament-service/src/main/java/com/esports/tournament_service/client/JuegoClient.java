package com.esports.tournament_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JuegoClient {

    private static final Logger log = LoggerFactory.getLogger(JuegoClient.class);

    @Value("${game.service.url}")
    private String gameServiceUrl;

    private final RestTemplate restTemplate;

    public JuegoClient() {
        this.restTemplate = new RestTemplate();
    }

    public boolean juegoExiste(Long juegoId) {
        try {
            String url = gameServiceUrl + "/api/juegos/" + juegoId;
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (Exception e) {
            log.error("Error consultando game-service para juegoId {}: {}",
                    juegoId, e.getMessage());
            return false;
        }
    }
}