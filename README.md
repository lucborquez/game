# eSports Arena Manager

eSports Arena Manager es una plataforma backend distribuida basada en microservicios, contruida para gestionar torneos de videjuegos
competitivos de forma escalable y modular. Abarca el ciclo  completo: desde la administracion de jugadores y equipo hasta la generacion 
de rankings, sanciones y notificaciones en tiempo real.

## Integrantes
Lucas Borquez
Jhon Olivares

## Tecnologías
java 21


## Microservicios
|Servicio	|Puerto	|Base de datos|	Responsabilidad|
|---|---|---|---|
|user-service|8001|	user_service_db	|Identidades roles y estados de cuenta|
|game-service|8002|	game_service_db	|Catálogo de videojuegos y modalidades|
|team-service|8003|	team_service_db	|Equipos membresías y capitanes|
|tournament-service|8004|	tournament_service_db	|Motor de torneos y control de estados|
|registration-service|8005|	registration_service_db|	Inscripciones con validación de sanciones|
|match-service|8006|	match_service_db	|Programación de partidas y brackets|
|result-service|8007|	result_service_db	|Registro y validación de resultados|
|ranking-service|8008|	ranking_service_db|	Cálculo de posiciones y estadísticas|
|sanction-service|8009|	sanction_service_db|	Sanciones y sistema de Fair Play|
|notification-service|8010|	notification_service_db|	Notificaciones internas del sistema|

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
10. Ver notificaciones     -> notification-service



## Regla de negocio
User-service - 8001
RolDescripcionADMINISTRADORConfigura juegos y torneosORGANIZADORGestiona partidas y resultadosJUGADORParticipa en equipos y torneos

Usuario INACTIVO o SANCIONADO no puede competir ni inscribirse.

Team Service — 8003
El capitan debe existir y estar ACTIVO en user-service antes de crear el equipo.

Equipo INACTIVO no puede inscribirse en ningun torneo.

Tournament Service — 8004
BORRADOR -> ABIERTO -> EN_CURSO -> FINALIZADO

No se modifican reglas criticas con torneo en curso. La fecha de inicio debe ser posterior al cierre de inscripciones.

Registration Service — 8005
Validaciones antes de inscribir:

Torneo en estado ABIERTO
Cupos disponibles
Sin sancion activa bloqueante
Sin inscripcion duplicada en el mismo torneo

Sanction Service — 8009
SeveridadEfectoBAJASolo advertenciaMEDIARestriccion parcialALTABloqueo total de inscripciones

Sancion expirada no bloquea inscripciones futuras.


Ranking Service — 8008

Solo resultados con estado validado impactan el ranking. Las posiciones se recalculan automaticamente ante cada cambio de puntos.


Pendientes
ModuloDescripcionresult-serviceValidacion automatica de discrepancias en puntajesranking-serviceRecalculo masivo al cerrar torneo por abandononotification-serviceIntegracion con servicios de correo externossanction-serviceExpiracion automatica de sanciones por fecha
