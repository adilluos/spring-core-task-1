package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.dto.CreateTrainingRequest;
import com.adilzhan.firsttask.dto.TraineesTrainingsRequest;
import com.adilzhan.firsttask.dto.TrainersTrainingsRequest;
import com.adilzhan.firsttask.dto.TrainingRow;
import com.adilzhan.firsttask.service.web.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import java.time.LocalDate;
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

    @GetMapping("/get-trainees-trainings/{username}")
    public List<TrainingRow> getTraineeTrainings(@PathVariable
                                                 String username,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DATE)
                                                 LocalDate periodFrom,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DATE)
                                                 LocalDate periodTo,
                                                 @RequestParam(required = false)
                                                 String trainerName,
                                                 @RequestParam(required = false)
                                                 String trainingType
    ) {
        return trainingService.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType);
    }

    @GetMapping("/get-trainers-trainings/{username}")
    public List<TrainingRow> getTrainerTrainings(@PathVariable
                                                 String username,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DATE)
                                                 LocalDate periodFrom,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DATE)
                                                 LocalDate periodTo,
                                                 @RequestParam(required = false)
                                                 String traineeName
    ) {
        return trainingService.getTrainerTrainings(username, periodFrom, periodTo, traineeName);
    }

    @GetMapping("/get-training-types")
    public List<String> getAllTrainingTypes() {
        return trainingService.listAllTrainingTypes();
    }
}
