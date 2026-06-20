# Cómo conectar tus 9 microservicios a Eureka

Aplica estos 2 cambios en **cada uno** de: auth-service, game-service, match-service,
ranking-service, registration-service, result-service, sanction-service, team-service,
tournament-service.

---

## 1. En el `pom.xml` de cada servicio

Agrega esta propiedad dentro de `<properties>` (junto a `java.version`):

```xml
<spring-cloud.version>2025.1.1</spring-cloud.version>
```

Agrega esta dependencia dentro de `<dependencies>`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

Agrega este bloque completo, al mismo nivel que `<dependencies>` y `<build>` (NO dentro de
`<dependencies>`):

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

⚠️ Si ya tienes un `<dependencyManagement>` en ese pom, no crees uno nuevo: agrega la
`<dependency>` de `spring-cloud-dependencies` dentro del que ya existe.

---

## 2. En el `application.properties` de cada servicio

Agrega al final del archivo:

```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true
```

No necesitas tocar `spring.application.name` ni `server.port`: ya están bien puestos en
todos tus servicios y son justamente el nombre con el que Eureka los va a registrar.

---

## 3. Orden de arranque para probar (importante)

1. `eureka-server` (puerto 8761) — espera a que cargue completo.
2. Los 9 microservicios, en cualquier orden.
3. `api-gateway` (puerto 8080) — al último, porque también es cliente Eureka.
4. Verifica en **http://localhost:8761** que aparezcan los 9 servicios + el gateway
   registrados como `UP`.
5. Prueba a través del gateway, por ejemplo:
   `GET http://localhost:8080/api/usuarios` en vez de `GET http://localhost:8082/api/usuarios`.

---

## 4. Paso opcional (recomendado para la defensa): usar lb:// entre microservicios

Hoy tus servicios se llaman entre sí con URLs fijas, por ejemplo en
`tournament-service` → `game.service.url=http://localhost:8081`. Una vez que todos estén
en Eureka, puedes reemplazar esas URLs por el nombre del servicio y dejar que el
`LoadBalancerClient`/`WebClient.Builder` (anotado con `@LoadBalanced`) resuelva la IP/puerto
real. Esto demuestra mejor dominio de Eureka en la defensa (IE 2.4.1 / IE 2.4.3).

Si quieres, en el siguiente paso lo armamos juntos para 1-2 servicios como ejemplo y replicas
el patrón en los demás.
