package com.esports.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// El gateway es una app Spring Boot normal. Las rutas se definen en application.yml.
// Al tener el cliente Eureka en el classpath, tambien se registra en eureka-server
// y puede resolver las URIs lb://nombre-del-servicio.
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
