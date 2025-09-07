package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.dto.*;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.service.web.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {
    private final ProfileService profileService;

    @Autowired
    public RegistrationController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("trainee")
    public RegisterTraineeResponse registerTrainee(@RequestBody RegisterTraineeRequest req) {
        RegisterTraineeResponse response = profileService.createTrainee(req.firstName(), req.lastName(), req.dateOfBirth(), req.address());
        return response;
    }

    @PostMapping("trainer")
    public RegisterTrainerResponse registerTrainer(@RequestBody RegisterTrainerRequest req) {
        RegisterTrainerResponse response = profileService.createTrainer(req.firstName(), req.lastName(), req.specialization());
        return response;
    }
}
