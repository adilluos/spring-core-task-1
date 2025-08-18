//package com.adilzhan.firsttask.service.inMemory;
//
//import com.adilzhan.firsttask.dao.inMemoryDao.TrainerDao;
//import com.adilzhan.firsttask.model.Trainer;
//import com.adilzhan.firsttask.util.UsernamePasswordGenerator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class TrainerService {
//    private static final Logger log = LoggerFactory.getLogger(TrainerService.class);
//    private final TrainerDao trainerDao;
//
//    @Autowired
//    public TrainerService(TrainerDao trainerDao) {
//        this.trainerDao = trainerDao;
//    }
//
//    public Trainer createTrainer(String firstName, String lastName, String specialization) {
//        List<String> existingUsernames = trainerDao.findAll().stream()
//                .map(Trainer::getUsername)
//                .toList();
//        String username = UsernamePasswordGenerator.generateUsername(firstName, lastName, existingUsernames);
//        String password = UsernamePasswordGenerator.generatePassword();
//        String userId = UUID.randomUUID().toString();
//
//        log.info("Creating trainer with username: {}", username);
//
//        Trainer trainer = new Trainer(firstName, lastName, username, password, true, specialization, userId);
//        trainerDao.save(trainer);
//        return trainer;
//    }
//
//    public void updateTrainer(Trainer trainer) {
//        log.info("Updating trainer with ID: {}", trainer.getId());
//        trainerDao.update(trainer);
//    }
//
//    public Trainer getTrainerById(String userId) {
//        log.info("Fetching trainer by ID: {}", userId);
//        return trainerDao.findById(userId).orElse(null);
//    }
//
//    public List<Trainer> getAllTrainers() {
//        log.info("Fetching all trainers");
//        return trainerDao.findAll();
//    }
//}
