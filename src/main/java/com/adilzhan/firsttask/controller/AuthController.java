package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.exception.InvalidCredentialsException;
import com.adilzhan.firsttask.service.web.AuthService;
import com.adilzhan.firsttask.service.web.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ProfileService profileService;

    @Autowired
    public AuthController(AuthService authService, ProfileService profileService) {
        this.authService = authService;
        this.profileService = profileService;
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(
            @RequestParam String username,
            @RequestParam String password
    ){
        if (!authService.authenticate(username, password)) {
            throw new InvalidCredentialsException("Invalid username and password");
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
