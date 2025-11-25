package com.example.huerto.huerto.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Clave secreta para firmar los tokens (se genera al arrancar la app)
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generar un token con usuario y rol
    public String generateToken(String username, String role) {
        long now = System.currentTimeMillis();
        long expirationMillis = 24 * 60 * 60 * 1000; // 24 horas

        return Jwts.builder()
                .setSubject(username)          // normalmente el correo
                .claim("role", role)           // guardamos el rol
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMillis))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Extraer username (subject)
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extraer rol
    public String extractRole(String token) {
        return (String) Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }

    // Validar token (firma, expiración, etc.)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // aquí podrías loguear la excepción
            return false;
        }
    }
}
