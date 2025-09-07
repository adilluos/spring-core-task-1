package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.exception.InvalidCredentialsException;
import com.adilzhan.firsttask.service.web.AuthService;
import com.adilzhan.firsttask.service.web.ProfileService;
import com.adilzhan.firsttask.service.web.security.FailedLoginService;
import com.adilzhan.firsttask.service.web.security.JwtService;
import com.adilzhan.firsttask.service.web.security.TokenBlacklist;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ProfileService profileService;
    private final JwtService jwtService;
    private final FailedLoginService failedLoginService;
    private final TokenBlacklist tokenBlacklist;

    @Autowired
    public AuthController(AuthService authService, ProfileService profileService, JwtService jwtService, FailedLoginService failedLoginService, TokenBlacklist tokenBlacklist) {
        this.authService = authService;
        this.profileService = profileService;
        this.jwtService = jwtService;
        this.failedLoginService = failedLoginService;
        this.tokenBlacklist = tokenBlacklist;
    }

    @PostMapping("/login")
    public void login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response
    ){
        if (failedLoginService.isLocked(username)) {
            throw new ResponseStatusException(HttpStatus.LOCKED, "Account locked. Try again later.");
        }

        boolean ok = authService.authenticate(username, password);
        if (!ok) {
            failedLoginService.onFailure(username);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        failedLoginService.onSuccess(username);
        String token = jwtService.generate(username);
        response.setHeader("Authorization", "Bearer " + token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Jws<Claims> jws = jwtService.parse(token);
                Instant exp = jws.getBody().getExpiration().toInstant();
                tokenBlacklist.revoke(token, exp);
            } catch (JwtException e) {
                System.out.println("Nothing to revoke!!!!");
            }
        }
    }

    @PutMapping("/changePassword")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(
            @RequestParam String username,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        profileService.changePassword(username, oldPassword, newPassword);
    }
}
