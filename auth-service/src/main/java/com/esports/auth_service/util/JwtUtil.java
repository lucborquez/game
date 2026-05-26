package com.esports.auth_service.util;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    //Lee el valor de jwt.secret del applicattion.properties
    @Value("${jwt.secret}")
    private String secret;

    //Lee el valor de jwt.expiration del applicattion.properties
    @Value("${jwt.expiration}")
    private Long expiration;

    //Genera la clave de firma a partir de secret
    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //Crea un nuevo token JWT con el email y rol de usuario
    public String generarToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    //Extrae el email del token
    public String obtenerEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //Verifica si el token es valido
    public boolean esValido(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.warn("Token invalido: {}", e.getMessage());
            return false;
        }
    }
}
