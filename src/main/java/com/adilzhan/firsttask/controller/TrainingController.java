package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.dto.CreateTrainingRequest;
import com.adilzhan.firsttask.dto.TraineesTrainingsRequest;
import com.adilzhan.firsttask.dto.TrainersTrainingsRequest;
import com.adilzhan.firsttask.dto.TrainingRow;
import com.adilzhan.firsttask.service.web.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/training")
public class TrainingController {
    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("/add-training")
    public void addTraining(@RequestBody CreateTrainingRequest request) {
        trainingService.addTraining(request.trainerUsername(), request.traineeUsername(), request.typeCode(), request.date(), request.duration(), request.description());
    }

    @GetMapping("/get-trainees-trainings")
    public List<TrainingRow> getTraineeTrainings(@RequestBody TraineesTrainingsRequest request) {
        return trainingService.getTraineeTrainings(request.username(), request.periodFrom(), request.periodTo(), request.trainerName(), request.trainingType());
    }

    @GetMapping("/get-trainers-trainings")
    public List<TrainingRow> getTrainerTrainings(@RequestBody TrainersTrainingsRequest request) {
        return trainingService.getTrainerTrainings(request.username(), request.periodFrom(), request.periodTo(), request.traineeName());
    }

    @GetMapping("/get-training-types")
    public List<String> getAllTrainingTypes() {
        return trainingService.listAllTrainingTypes();
    }
}
