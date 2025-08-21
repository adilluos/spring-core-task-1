package com.adilzhan.firsttask.service.web;

import com.adilzhan.firsttask.dto.TrainerOption;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.model.Training;
import com.adilzhan.firsttask.model.TrainingType;
import com.adilzhan.firsttask.repository.TraineeRepository;
import com.adilzhan.firsttask.repository.TrainerRepository;
import com.adilzhan.firsttask.repository.TrainingRepository;
import com.adilzhan.firsttask.repository.TrainingTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TrainingService {
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository typeRepository;


    public TrainingService(TraineeRepository traineeRepository, TrainerRepository trainerRepository, TrainingRepository trainingRepository, TrainingTypeRepository typeRepository) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
        this.typeRepository = typeRepository;
    }

    @Transactional
    public Training addTraining(String trainerUsername,
                                String traineeUsername,
                                String typeCode,
                                LocalDate date,
                                Integer duration,
                                String description) {
        if (date == null || duration == null || duration <= 0 || description == null || description.isBlank())
            throw new IllegalArgumentException("date, positive duration and description are required");
        Trainer trainer = trainerRepository.findByUsername(trainerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + trainerUsername));
        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + traineeUsername));
        TrainingType trainingType = typeRepository.findByCode(typeCode)
                .orElseThrow(() -> new IllegalArgumentException("Training type not found: " + typeCode));

        String id = UUID.randomUUID().toString();
        Training training = new Training(id, trainer, trainee, trainingType, date, duration, description);
        trainingRepository.save(training);
        return training;
    }

    @Transactional
    public List<TrainerOption> getAvailableTrainersForTrainee(String traineeUsername) {
        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + traineeUsername));
        return trainerRepository.findActiveNotAssignedTo(trainee.getUsername());
    }

//    public List<Training> getTraineeTrainings

}
