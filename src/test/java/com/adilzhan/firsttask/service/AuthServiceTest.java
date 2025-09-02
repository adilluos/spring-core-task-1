package com.adilzhan.firsttask.service;

import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.repository.UserRepository;
import com.adilzhan.firsttask.service.web.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock //fake object
    private UserRepository userRepository;

    @InjectMocks //real object that is injected by fake objects
    private AuthService authService;

    @Test
    void authenticate_ok() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");
        when(userRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        boolean authenticated = authService.authenticate("Ali.Uly", "12345");

        assertTrue(authenticated);
        verify(userRepository).findByUsername("Ali.Uly");
    }

    @Test
    void authenticate_badPassword() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");
        when(userRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        assertFalse(authService.authenticate("Ali.Uly", "9999"));
    }

    @Test
    void authenticate_userNotFound() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");
        lenient().when(userRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        assertFalse(authService.authenticate("Not.User", "12345"));
    }
}
