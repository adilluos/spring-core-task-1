package com.adilzhan.firsttask.service.web;

import com.adilzhan.firsttask.client.WorkloadClient;
import com.adilzhan.firsttask.dto.*;
import com.adilzhan.firsttask.dto.WorkloadUpdateRequest;
import com.adilzhan.firsttask.messaging.WorkloadMessageProducer;
import com.adilzhan.firsttask.metrics.TrainingMetrics;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.model.Training;
import com.adilzhan.firsttask.model.TrainingType;
import com.adilzhan.firsttask.repository.TraineeRepository;
import com.adilzhan.firsttask.repository.TrainerRepository;
import com.adilzhan.firsttask.repository.TrainingRepository;
import com.adilzhan.firsttask.repository.TrainingTypeRepository;
import com.adilzhan.firsttask.service.integration.WorkloadClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrainingService {
    private static final Logger log = LoggerFactory.getLogger(TrainingService.class);

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository typeRepository;
    private final TrainingMetrics trainingMetrics;
    private final WorkloadClientService workloadClientService;
    private final WorkloadMessageProducer workloadMessageProducer;


    public TrainingService(TraineeRepository traineeRepository, TrainerRepository trainerRepository, TrainingRepository trainingRepository, TrainingTypeRepository typeRepository, TrainingMetrics trainingMetrics, WorkloadClientService workloadClientService, WorkloadMessageProducer workloadMessageProducer) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
        this.typeRepository = typeRepository;
        this.trainingMetrics = trainingMetrics;
        this.workloadClientService = workloadClientService;
        this.workloadMessageProducer = workloadMessageProducer;
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


        WorkloadUpdateRequest request = new WorkloadUpdateRequest(
                trainer.getUsername(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.isActive(),
                training.getTrainingDate(),
                training.getDuration(),
                "ADD"
        );
        //todo logging connected with workload-service

//        try {
//            workloadClientService.sendWorkloadUpdate(request);
//            System.out.println("Workload update sent to workload-service");
//        } catch (Exception ex) {
//            System.err.println("Failed to notify workload-service: " + ex.getMessage());
//        }
        workloadMessageProducer.sendWorkloadUpdate(request);

        trainingMetrics.incTrainingCreated(trainingType.getCode());
        return training;
    }

    @Transactional
    public List<TrainerOption> getAvailableTrainersForTrainee(String traineeUsername) {
        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + traineeUsername));
        return trainerRepository.findActiveNotAssignedTo(trainee.getUsername());
    }

    @Transactional
    public Trainee setTraineeTrainers(String traineeUsername, List<String> trainerUsernames) {
        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + traineeUsername));
        Set<Trainer> newSet = trainerUsernames == null ? Collections.emptySet()
                : trainerUsernames.stream()
                .map(u -> trainerRepository.findByUsername(u)
                        .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + u)))
                .collect(Collectors.toSet());
        trainee.getTrainers().clear();
        trainee.getTrainers().addAll(newSet);
        log.info("Updated Trainers List for Trainee: {} trainers were added to Trainee - {}", newSet.size(), traineeUsername);
        return trainee;
    }

    @Transactional
    public List<TrainingRow> getTraineeTrainings(
            String username, LocalDate from, LocalDate to, String trainerName, String trainingType) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username is required");
        }
        return trainingMetrics.timeSearch("trainee", () -> trainingRepository.findTraineeTrainings(username, from, to, emptyToNull(trainerName), emptyToNull(trainingType)));
    }

    @Transactional
    public List<TrainingRow> getTrainerTrainings(
            String username, LocalDate from, LocalDate to, String traineeName) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username is required");
        }
        return trainingMetrics.timeSearch("trainer", () -> trainingRepository.findTrainerTrainings(username, from, to, emptyToNull(traineeName)));
    }

    @Transactional
    public List<String> listAllTrainingTypes() {
        return typeRepository.findAll().stream()
                .map(TrainingType::getCode)
                .toList();
    }

    private String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}
