package com.esports.match_service.client;

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

    public boolean torneoExiste(Long torneoId) {
        try {
            String url = tournamentServiceUrl + "/api/torneos/" + torneoId;
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (Exception e) {
            log.error("Error consultando tournament-service para torneoId {}: {}",
                    torneoId, e.getMessage());
            return false;
        }
    }
}