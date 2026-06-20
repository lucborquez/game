package com.esports.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// @EnableEurekaServer convierte esta app en el servidor de descubrimiento.
// Los 9 microservicios de EsportsArenaManager se registran aqui (con su
// spring.application.name) y se buscan entre si por nombre, en vez de usar
// localhost:puerto fijo.
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
