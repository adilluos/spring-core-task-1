package com.adilzhan.firsttask.service.web.security;

import com.adilzhan.firsttask.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TokenBlacklist blacklist;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, TokenBlacklist blacklist, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.blacklist = blacklist;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                if (!blacklist.isRevoked(token)) {
                    Jws<Claims> jws = jwtService.parse(token);

                    String username = jws.getBody().getSubject();

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (JwtException e) {
                System.out.println("JWT EXCEPTION!!!! >:(");
            }
        }
        chain.doFilter(req, res);
    }
}
