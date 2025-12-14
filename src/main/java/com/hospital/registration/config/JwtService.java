package com.hospital.registration.config;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final Key key;
    private final long expMinutes;

    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expirationMinutes}") long expMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expMinutes = expMinutes;
    }

    public String generateToken(String userId, String profile, String email) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expMinutes * 60);

        Map<String, Object> claims = new HashMap<>();
        claims.put("profile", profile);

        if (email != null) {
            claims.put("email", email);
        }

        return Jwts.builder()
                .subject(userId)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parser().verifyWith((javax.crypto.SecretKey) key).build().parseSignedClaims(token);
    }

    public boolean isValid(String token) {
        try {
            parse(token);
            return true;
        } catch (Exception e) {
            return false; // invalid / expired / malformed
        }
    }

    public String getUserId(String token) {
        return parse(token)
                .getPayload()
                .getSubject();
    }

    public String getProfile(String token) {
        return parse(token)
                .getPayload()
                .get("profile", String.class);
    }

    public String getEmail(String token) {
        return parse(token)
                .getPayload()
                .get("email", String.class);
    }
}
