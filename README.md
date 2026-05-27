# eSports Arena Manager

eSports Arena Manager es una plataforma backend orientada a la administración de torneos competitivos de videojuegos.
El sistema fue desarrollado bajo una arquitectura de microservicios utilizando Spring Boot, permitiendo separar responsabilidades y mantener una solución modular, escalable y mantenible.

El objetivo principal del proyecto es gestionar torneos de eSports mediante servicios independientes encargados de usuarios, equipos, partidas, resultados, rankings, sanciones y procesos relacionados al flujo competitivo.

La solución fue diseñada aplicando buenas prácticas de desarrollo backend, persistencia relacional, validaciones, manejo de excepciones y comunicación REST entre microservicios.

## Integrantes
Lucas Borquez
Jhon Olivares

## Arquitectura General del Sistem
La solución fue diseñada bajo una arquitectura de microservicios independientes, donde cada servicio posee:

1.Responsabilidad específica
 2.Base de datos independiente
 3.CRUD propio
 4.Endpoints REST
 5.Reglas de negocio
 6.Validaciones
 7.Manejo de excepciones
 8.Comunicación REST con otros servicios


## Microservicios
|Servicio	|Puerto	|Base de datos|	Responsabilidad|
|---|---|---|---|
|user-service|8082|	user_service_db	|Identidades roles y estados de cuenta|cambiado---
|game-service|8081|	game_service_db	|Catálogo de videojuegos y modalidades| cambiado---
|team-service|8084|	team_service_db	|Equipos membresías y capitanes|cambiado---
|tournament-service|8085|	tournament_service_db	|Motor de torneos y control de estados|--cambiado
|registration-service|8086|	registration_service_db|	Inscripciones con validación de sanciones|cambiado---
|match-service|8087|	match_service_db	|Programación de partidas y brackets| cambiado----
|result-service|8088|	result_service_db	|Registro y validación de resultados| cambiado ---
|ranking-service|8008|	ranking_service_db|	Cálculo de posiciones y estadísticas|
|sanction-service|8090|	sanction_service_db|	Sanciones y sistema de Fair Play|---cambiado
|auth-service| 8083|auth-service_db|Administración de perfiles de usuario y roles del sistema.|cambiado---

## Tecnologias usadas
## Backend
1. Java 21
2. Spring boot
3. Spring Data JPA
4. Hibernate
5. Maven
6. Bean Validation
7. REST API

## BASE DE DATOS
Para la persistencia se utilizó:
1. MySQL
2. Laragon
3. HeidiSQL
Laragon fue utilizado como entorno local de desarrollo debido a su facilidad de configuración, integración rápida con MySQL y administración eficiente de servicios locales.
HeidiSQL fue utilizado para administrar las bases de datos, permitiendo visualizar tablas, ejecutar consultas SQL y verificar relaciones de manera más profesional y práctica durante el desarrollo.

## Flujo principal
1. Crear videojuego        -> game-service
2. Crear usuarios          -> user-service
3. Crear equipo            -> team-service  (requiere usuario activo y juego)
4. Crear torneo            -> tournament-service  (requiere juego)
5. Abrir torneo            -> tournament-service  PUT /estado?nuevo=ABIERTO
6. Inscribir equipo        -> registration-service  (valida sanciones y cupos)
7. Crear partida           -> match-service  (valida inscripciones)
8. Registrar resultado     -> result-service
9. Consultar ranking       -> ranking-service
10. Registrar sanción      ->sanction-service
(bloquea futuras inscripciones si la sanción está activa)



## . Capa de Modelo y Anotaciones
Se utiliza el ecosistema de Spring Data JPA y Lombok para maximizar la productividad y garantizar la integridad de los datos:

Lombok: @Data, @AllArgsConstructor, @NoArgsConstructor, @Builder y @Getter/@Setter para reducir el código redundante.

JPA/Hibernate: @Entity, @Table, @Id, @GeneratedValue(strategy = GenerationType.IDENTITY) y @Column para el mapeo relacional preciso.

Jakarta Validation: @NotBlank, @NotNull, @Positive y @Size en los DTOs para la validación de entrada de datos.
## Patrón Arquitectónico CSR
Todos los microservicios fueron estructurados utilizando el patrón:

Controller → manejo de endpoints REST
Service → lógica de negocio
Repository → acceso a datos
Model → entidades JPA
DTO → validaciones y transferencia de datos

## Conclusión
El proyecto eSports Arena Manager permitió aplicar conocimientos relacionados con arquitectura distribuida basada en microservicios, persistencia relacional,
desarrollo REST, validaciones, manejo de excepciones y comunicación entre servicios.
