# eSports Arena Manager

eSports Arena Manager es una plataforma backend distribuida basada en microservicios, contruida para gestionar torneos de videjuegos
competitivos de forma escalable y modular. Abarca el ciclo  completo: desde la administracion de jugadores y equipo hasta la generacion 
de rankings, sanciones y notificaciones en tiempo real.

## Integrantes
Lucas Borquez
Jhon Olivares

## Tecnologías
java 26


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

## Como ejecuturar
(pendiente)

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
10. Crear sancion(si es neseario)


## 1. Capa de Modelo y Anotaciones
Se utiliza el ecosistema de Spring Data JPA y Lombok para maximizar la productividad y garantizar la integridad de los datos:

Lombok: @Data, @AllArgsConstructor, @NoArgsConstructor, @Builder y @Getter/@Setter para reducir el código redundante.

JPA/Hibernate: @Entity, @Table, @Id, @GeneratedValue(strategy = GenerationType.IDENTITY) y @Column para el mapeo relacional preciso.

Jakarta Validation: @NotBlank, @NotNull, @Positive y @Size en los DTOs para la validación de entrada de datos.
## Regla de negocio

