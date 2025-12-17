package com.adilzhan.firsttask.service.web.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class ServiceTokenProvider {
    private final Key key;
    private final String issuer;
    private final String audience;
    private final long ttlMinutes;

    public ServiceTokenProvider(
            @Value("${service-auth.shared-secret}") String secret,
            @Value("${service-auth.issuer}") String issuer,
            @Value("${service-auth.audience}") String audience,
            @Value("${service-auth.ttl-minutes}") long ttlMinutes
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
        this.audience = audience;
        this.ttlMinutes = ttlMinutes;
    }

    public String newToken() {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ttlMinutes * 60);
        return Jwts.builder()
                .setIssuer(issuer)
                .setAudience(audience)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .claim("svc", issuer)
                .signWith(key)
                .compact();
    }
}
