package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.exception.InvalidCredentialsException;
import com.adilzhan.firsttask.repository.UserRepository;
import com.adilzhan.firsttask.service.web.AuthService;
import com.adilzhan.firsttask.service.web.ProfileService;
import com.adilzhan.firsttask.service.web.security.FailedLoginService;
import com.adilzhan.firsttask.service.web.security.JwtService;
import com.adilzhan.firsttask.service.web.security.TokenBlacklist;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.Instant;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private FailedLoginService failedLoginService;
    @MockitoBean
    private ProfileService profileService;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklist tokenBlacklist;
    @MockitoBean
    private UserRepository userRepository;



    @Test
    public void successfulLogin() throws Exception {
        when(failedLoginService.isLocked("Ali.Uly")).thenReturn(false);
        when(authService.authenticate("Ali.Uly", "testtest")).thenReturn(true);
        when(jwtService.generate("Ali.Uly")).thenReturn("jwt.token");

        ResultActions response = mockMvc.perform(post("/api/v1/auth/login")
                        .param("username", "Ali.Uly")
                        .param("password", "testtest"))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer jwt.token"))
                .andExpect(header().string("Access-Control-Expose-Headers", "Authorization"));

        verify(failedLoginService).onSuccess("Ali.Uly");
    }

    @Test
    public void unsuccessfulLogin_wrongPassword() throws Exception {
        when(failedLoginService.isLocked("Ali.Uly")).thenReturn(false);
        when(authService.authenticate("Ali.Uly", "testtest")).thenReturn(true);
        when(jwtService.generate("Ali.Uly")).thenReturn("jwt.token");

        ResultActions response = mockMvc.perform(post("/api/v1/auth/login")
                        .param("username", "Ali.Uly")
                        .param("password", "wrongPassword"))
                .andExpect(status().isUnauthorized());

        verify(failedLoginService).onFailure("Ali.Uly");
    }

    @Test
    public void successfulLogout() throws Exception {
        String token = "valid.token";

        Claims claims = mock(Claims.class);
        when(claims.getExpiration()).thenReturn(Date.from(Instant.now().plusSeconds(900)));

        Jws<Claims> jws = mock(Jws.class);
        when(jws.getBody()).thenReturn(claims);

        when(jwtService.parse(token)).thenReturn(jws);
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(tokenBlacklist).revoke(eq(token), any(Instant.class));
    }

    @Test
    void logout_noAuthorizationHeader() throws Exception {

        mockMvc.perform(post("/api/v1/auth/logout"))
                .andExpect(status().isOk());

        verify(tokenBlacklist, never()).revoke(any(), any());
        verify(jwtService, never()).parse(any());
    }

    @Test
    void logout_invalidToken() throws Exception {
        String token = "invalid.token";

        when(jwtService.parse(token)).thenThrow(new JwtException("Bad token"));

        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(tokenBlacklist, never()).revoke(any(), any());
    }

    @Test
    void changePassword_success() throws Exception {

        mockMvc.perform(put("/api/v1/auth/changePassword")
                        .param("username", "Ali")
                        .param("oldPassword", "old123")
                        .param("newPassword", "new123"))
                .andExpect(status().isOk());

        verify(profileService)
                .changePassword("Ali", "old123", "new123");
    }

    @Test
    void changePassword_invalidOldPassword() throws Exception {
        doThrow(new InvalidCredentialsException("Authentication failed"))
                .when(profileService)
                .changePassword("Ali", "wrongOld", "new123");

        mockMvc.perform(put("/api/v1/auth/changePassword")
                        .param("username", "Ali")
                        .param("oldPassword", "wrongOld")
                        .param("newPassword", "new123"))
                .andExpect(status().isUnauthorized());
    }

}
