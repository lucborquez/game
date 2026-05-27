package com.esports.resultservice.client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MatchClient {

    private static final Logger log = LoggerFactory.getLogger(MatchClient.class);

    // Lee el "http://localhost:8087" que configuramos en tu application.properties
    @Value("${match.service.url}")
    private String matchServiceUrl;

    private final RestTemplate restTemplate;

    public MatchClient() {
        this.restTemplate = new RestTemplate();
    }

    //metodo que usa ResultadoService para verificar si la partida existe
    public boolean verificarPartidaExiste(Long partidaId) {
        try {
            // Arma la URL apuntando al controlador de tu amigo (ej: http://localhost:8087/api/partidas/1)
            String url = matchServiceUrl + "/api/partidas/" + partidaId;

            // Le pega al endpoint. Si la partida existe, el microservicio de tu compa responde bien (200 OK)
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (Exception e) {
            // Si la partida no existe (404) o el puerto 8087 está apagado, salta acá
            log.error("Error consultando match-service para partidaId {}: {}",
                    partidaId, e.getMessage());
            return false;
        }
    }
}