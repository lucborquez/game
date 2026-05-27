package com.esports.resultservice.client;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

@Component
public class ResultadoClient {

    private static final Logger log = LoggerFactory.getLogger(ResultadoClient.class);

    // Lee la URL de tu propio servicio si te vas a pegar a ti mismo, o cámbiala en el properties
    @Value("${result.service.url:http://localhost:8083}")
    private String resultServiceUrl;

    private final RestTemplate restTemplate;

    public ResultadoClient() {
        this.restTemplate = new RestTemplate();
    }

    //metodo para buscar un resultado conusmiendo el endpoitt GET
    public ResultadoDTO obtenerResultadoPorId(Long resultadoId) {
        try {
            String url = resultServiceUrl + "/api/resultados/" + resultadoId;
            return restTemplate.getForObject(url, ResultadoDTO.class);
        } catch (Exception e) {
            log.error("Error consultando result-service para resultadoId {}: {}",
                    resultadoId, e.getMessage());
            return null;
        }
    }

    // DTO espejo interno para manejar los datos del resultado de forma limpia
    @Data
    public static class ResultadoDTO {
        private Long id;
        private Long partidaId;
        private Long ganadorId;
        private Integer puntajeA;
        private Integer puntajeB;
        private String estadoValidacion;
        private String evidenciaUrl;
        private String justificacionAnulacion;
        private LocalDateTime fechaRegistro;
    }
}