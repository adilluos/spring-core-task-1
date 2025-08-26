package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.dto.*;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.service.web.ProfileService;
import com.adilzhan.firsttask.service.web.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainee")
public class TraineeController {

    private final ProfileService profileService;
    private final TrainingService trainingService;

    @Autowired
    public TraineeController(ProfileService profileService, TrainingService trainingService) {
        this.profileService = profileService;
        this.trainingService = trainingService;
    }

    @GetMapping("/getTraineeProfile/{username}")
    public TraineeProfileResponse getTrainee(@PathVariable String username) {
        Trainee trainee = profileService.getTraineeByUsername(username);
        List<TrainerSummary> trainers = trainee.getTrainers().stream()
                .map(trainer -> new TrainerSummary(
                        trainer.getUsername(),
                        trainer.getFirstName(),
                        trainer.getLastName(),
                        trainer.getSpecialization()
                ))
                .toList();
        return new TraineeProfileResponse(trainee.getFirstName(), trainee.getLastName(), trainee.getDateOfBirth(), trainee.getAddress(), trainee.isActive(), trainers);
    }

    @PutMapping("/updateTraineeProfile")
    public UpdateTraineeResponse updateTrainee(@RequestBody UpdateTraineeRequest request) {
        Trainee trainee = profileService.updateTraineeProfile(request.username(), request.dateOfBirth(), request.address());
        List<TrainerSummary> trainers = trainee.getTrainers().stream()
                .map(trainer -> new TrainerSummary(
                        trainer.getUsername(),
                        trainer.getFirstName(),
                        trainer.getLastName(),
                        trainer.getSpecialization()
                ))
                .toList();
        return new UpdateTraineeResponse(trainee.getUsername(), trainee.getFirstName(), trainee.getLastName(), trainee.getDateOfBirth(), trainee.getAddress(), trainee.isActive(), trainers);
    }

    @DeleteMapping("/deleteTrainee/{username}")
    public void deleteTrainee(@PathVariable String username) {
        profileService.deleteTraineeByUsername(username);
    }

    @GetMapping("/available-trainers/{username}")
    public List<TrainerOption> getAvailableTrainers(@PathVariable String username) {
        return trainingService.getAvailableTrainersForTrainee(username);
    }

    @PutMapping("/update-trainers-list-for-trainees/{username}")
    public UpdateTraineeTrainersResponse updateTraineeTrainersList(
            @PathVariable String username,
            @RequestBody UpdateTraineeTrainersRequest request
    ) {
        Trainee trainee = trainingService.setTraineeTrainers(username, request.trainerUsernames());
        List<TrainerSummary> trainers = trainee.getTrainers().stream()
                .map(trainer -> new TrainerSummary(
                        trainer.getUsername(),
                        trainer.getFirstName(),
                        trainer.getLastName(),
                        trainer.getSpecialization()
                ))
                .toList();
        return new UpdateTraineeTrainersResponse(trainers);
    }

    @PatchMapping("/activate/{username}")
    public void activateTrainee(@PathVariable String username) {
        profileService.activate(username);
    }

    @PatchMapping("/deactivate/{username}")
    public void deactivateTrainee(@PathVariable String username) {
        profileService.deactivate(username);
    }
}
