package com.adilzhan.firsttask.service.web.security;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklist {
    private final ConcurrentHashMap<String, Instant> revoked = new ConcurrentHashMap<>();

    public void revoke(String token, Instant expiresAt) {
        revoked.put(token, expiresAt);
    }

    public boolean isRevoked(String token) {
        Instant exp = revoked.get(token);
        if (exp == null) return false;
        if (exp.isBefore(Instant.now())) {
            revoked.remove(token);
            return false;
        }
        return true;
    }
}
