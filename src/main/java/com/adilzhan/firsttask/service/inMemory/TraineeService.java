//package com.adilzhan.firsttask.service.inMemory;
//
//import com.adilzhan.firsttask.dao.inMemoryDao.TraineeDao;
//import com.adilzhan.firsttask.model.Trainee;
//import com.adilzhan.firsttask.util.UsernamePasswordGenerator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class TraineeService {
//    private final TraineeDao traineeDao;
//
//    private static final Logger log = LoggerFactory.getLogger(TraineeService.class);
//
//    @Autowired
//    public TraineeService(TraineeDao traineeDao) {
//        this.traineeDao = traineeDao;
//    }
//
//    public Trainee createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
//        List<String> existingUsernames = traineeDao.findAll().stream()
//                .map(Trainee::getUsername)
//                .toList();
//
//        String username = UsernamePasswordGenerator.generateUsername(firstName, lastName, existingUsernames);
//        String password = UsernamePasswordGenerator.generatePassword();
//        String userId = UUID.randomUUID().toString();
//
//        log.info("Creating trainee with username: {}", username);
//
//        Trainee trainee = new Trainee(firstName, lastName, username, password, true, dateOfBirth, address, userId);
//        traineeDao.save(trainee);
//        return trainee;
//    }
//
//    public void updateTrainee(Trainee trainee) {
//        log.info("Updating trainee with ID: {}", trainee.getId());
//        traineeDao.update(trainee);
//    }
//
//    public void deleteTrainee(String userId) {
//        log.warn("Deleting trainee: {}", userId);
//        traineeDao.delete(userId);
//    }
//
//    public Trainee getTraineeById(String userId) {
//        log.info("Fetching trainee by ID: {}", userId);
//        return traineeDao.findById(userId).orElse(null);
//    }
//
//    public List<Trainee> getAllTrainees() {
//        log.info("Fetching all trainees");
//        return traineeDao.findAll();
//    }
//}
