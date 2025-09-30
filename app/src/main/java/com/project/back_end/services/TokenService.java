package com.project.back_end.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private static final long EXPIRATION_TIME = 604_800_000; // 7 days

    public String generateToken(String identifier, String role) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(identifier)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String extractIdentifier(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    public boolean validateToken(String token, String expectedRole) {
        try {
            SecretKey key = Keys.hmacShaKeyfor(secret.getBytes());
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            String role = claims.get("role", String.class);
            return expectedRole.equalsIgnoreCase(role) && !claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }
}
