package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.dto.TraineeSummary;
import com.adilzhan.firsttask.dto.TrainerProfileResponse;
import com.adilzhan.firsttask.dto.UpdateTrainerRequest;
import com.adilzhan.firsttask.dto.UpdateTrainerResponse;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.service.web.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainer")
public class TrainerController {

    private final ProfileService profileService;

    @Autowired
    public TrainerController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/getTrainerProfile/{username}")
    public TrainerProfileResponse getTrainer(@PathVariable String username) {
        Trainer trainer = profileService.getTrainerByUsername(username);
        List<TraineeSummary> trainees = trainer.getTrainees().stream()
                .map(trainee -> new TraineeSummary(
                        trainee.getUsername(),
                        trainee.getFirstName(),
                        trainee.getLastName()
                )).toList();
        return new TrainerProfileResponse(trainer.getFirstName(), trainer.getLastName(), trainer.getSpecialization(), trainer.isActive(), trainees);
    }

    @PutMapping("/updateTrainerProfile")
    public UpdateTrainerResponse updateTrainer(@RequestBody UpdateTrainerRequest request) {
        Trainer trainer = profileService.updateTrainerProfile(request.username(), request.specialization());
        List<TraineeSummary> trainees = trainer.getTrainees().stream()
                .map(trainee -> new TraineeSummary(
                        trainee.getUsername(),
                        trainee.getFirstName(),
                        trainee.getLastName()
                )).toList();
        return new UpdateTrainerResponse(trainer.getUsername(), trainer.getFirstName(), trainer.getLastName(), trainer.getSpecialization(), trainer.isActive(), trainees);
    }

    @PatchMapping("/activate/{username}")
    public void activateTrainer(@PathVariable String username) {
        profileService.activate(username);
    }

    @PatchMapping("/deactivate/{username}")
    public void deactivateTrainer(@PathVariable String username) {
        profileService.deactivate(username);
    }
}
