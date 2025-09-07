package com.adilzhan.firsttask.service.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FailedLoginService {

    private final int maxAttempts;
    private final Duration lockDuration;
    private final ConcurrentHashMap<String, Attempt> attemps = new ConcurrentHashMap<>();

    public FailedLoginService(
            @Value("${security.bruteForce.maxAttempts:3}") int maxAttempts,
            @Value("${security.bruteForce.lockMinutes:5}") long lockMinutes) {
        this.maxAttempts = maxAttempts;
        this.lockDuration = Duration.ofMinutes(lockMinutes);
    }

    public boolean isLocked(String username) {
        Attempt a = attemps.get(username);
        return a != null && a.lockedUntil != null && a.lockedUntil.isAfter(Instant.now());
    }

    public void onFailure(String username) {
        attemps.compute(username, (k, a) -> {
            if (a == null) a = new Attempt();
            a.count++;
            if (a.count >= maxAttempts) a.lockedUntil = Instant.now().plus(lockDuration);
            return a;
        });
    }

    public void onSuccess(String username) {
        attemps.remove(username);
    }

    static class Attempt { int count = 0; Instant lockedUntil; }
}
