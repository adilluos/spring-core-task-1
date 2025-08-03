package com.adilzhan.firsttask.service;

import com.adilzhan.firsttask.dao.TrainingDao;
import com.adilzhan.firsttask.model.Training;
import com.adilzhan.firsttask.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TrainingService {
    private static final Logger log = LoggerFactory.getLogger(TrainingService.class);
    private final TrainingDao trainingDao;

    @Autowired
    public TrainingService(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    public Training createTraining(String trainerUserId, String traineeUserId, String trainingName, TrainingType trainingType, LocalDate trainingDate, Duration trainingDuration) {
        String trainingId = UUID.randomUUID().toString();

        log.info("Creating training between trainer {} and trainee {} with type {}", trainerUserId, traineeUserId, trainingType);

        Training training = new Training(trainingId, trainerUserId, traineeUserId, trainingName, trainingType, trainingDate, trainingDuration);
        trainingDao.save(training);
        return training;
    }

    public Training getTrainingById(String id) {
        log.info("Fetching training by ID: {}", id);

        return trainingDao.findById(id).orElse(null);
    }

    public List<Training> getAllTrainings() {
        log.info("Fetching all trainings");

        return trainingDao.findAll();
    }
}
