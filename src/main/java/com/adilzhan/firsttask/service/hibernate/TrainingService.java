//package com.adilzhan.firsttask.service.hibernate;
//
//import com.adilzhan.firsttask.dao.hibernateDao.TraineeDao;
//import com.adilzhan.firsttask.dao.hibernateDao.TrainerDao;
//import com.adilzhan.firsttask.dao.hibernateDao.TrainingDao;
//import com.adilzhan.firsttask.dao.hibernateDao.TrainingTypeDao;
//import com.adilzhan.firsttask.model.Trainee;
//import com.adilzhan.firsttask.model.Trainer;
//import com.adilzhan.firsttask.model.Training;
//import com.adilzhan.firsttask.model.TrainingType;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class TrainingService {
//    private final TrainerDao trainerDao;
//    private final TraineeDao traineeDao;
//    private final TrainingDao trainingDao;
//    private final TrainingTypeDao trainingTypeDao;
//
//    public TrainingService(TrainerDao trainerDao, TraineeDao traineeDao, TrainingDao trainingDao, TrainingTypeDao trainingTypeDao) {
//        this.trainerDao = trainerDao;
//        this.traineeDao = traineeDao;
//        this.trainingDao = trainingDao;
//        this.trainingTypeDao = trainingTypeDao;
//    }
//
//    @Transactional
//    public Training addTraining(String trainerUsername,
//                                String traineeUsername,
//                                String typeCode,
//                                LocalDate date,
//                                Integer duration,
//                                String description) {
//        if (date == null || duration == null || duration <= 0 || description == null || description.isBlank())
//            throw new IllegalArgumentException("date, positive duration and description are required");
//        Trainer trainer = trainerDao.findByUsername(trainerUsername)
//                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + trainerUsername));
//        Trainee trainee = traineeDao.findByUsername(traineeUsername)
//                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + traineeUsername));
//        TrainingType trainingType = trainingTypeDao.findByCode(typeCode)
//                .orElseThrow(() -> new IllegalArgumentException("TrainingType not found: " + typeCode));
//
//        String id = UUID.randomUUID().toString();
//        Training training = new Training(id, trainer, trainee, trainingType, date, duration, description);
//        trainingDao.save(training);
//        return training;
//    }
//
//    public List<Training> getTraineeTrainings(String traineeUsername,
//                                              LocalDate from,
//                                              LocalDate to,
//                                              String trainerNameLike,
//                                              String typeCode) {
//        return trainingDao.findForTrainee(traineeUsername, from, to, trainerNameLike, typeCode);
//    }
//
//    public List<Training> getTrainerTrainings(String trainerUsername,
//                                              LocalDate from,
//                                              LocalDate to,
//                                              String traineeNameLike,
//                                              String typeCode) {
//        return trainingDao.findForTrainer(trainerUsername, from, to, traineeNameLike, typeCode);
//    }
//
//    @Transactional
//    public void setTraineeTrainers(String traineeUsername, List<String> trainerUsernames) {
//        Trainee trainee = traineeDao.findByUsername(traineeUsername)
//                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + traineeUsername));
//        Set<Trainer> newSet = trainerUsernames == null ? Collections.emptySet()
//                : trainerUsernames.stream()
//                .map(un -> trainerDao.findByUsername(un)
//                        .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + un)))
//                .collect(Collectors.toSet());
//        trainee.getTrainers().clear();
//        trainee.getTrainers().addAll(newSet);
//        traineeDao.update(trainee);
//    }
//}
