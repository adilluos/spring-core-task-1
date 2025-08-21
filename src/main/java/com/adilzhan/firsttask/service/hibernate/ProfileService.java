//package com.adilzhan.firsttask.service.hibernate;
//
//import com.adilzhan.firsttask.dao.hibernateDao.TraineeDao;
//import com.adilzhan.firsttask.dao.hibernateDao.TrainerDao;
//import com.adilzhan.firsttask.dao.hibernateDao.UserDao;
//import com.adilzhan.firsttask.model.Trainee;
//import com.adilzhan.firsttask.model.Trainer;
//import com.adilzhan.firsttask.model.User;
//import com.adilzhan.firsttask.util.UsernamePasswordGenerator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class ProfileService {
//    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);
//
//    private final UserDao userDao;
//    private final TraineeDao traineeDao;
//    private final TrainerDao trainerDao;
//    private final AuthService authService;
//
//    public ProfileService(UserDao userDao, TraineeDao traineeDao, TrainerDao trainerDao, AuthService authService) {
//        this.userDao = userDao;
//        this.traineeDao = traineeDao;
//        this.trainerDao = trainerDao;
//        this.authService = authService;
//    }
//
//    @Transactional
//    public Trainer createTrainer(String firstName, String lastName, String specialization) {
//        String username = nextUsername(firstName, lastName);
//        String password = UsernamePasswordGenerator.generatePassword();
//
//        String id = UUID.randomUUID().toString();
//        Trainer trainer = new Trainer(id, firstName, lastName, username, password, true, specialization);
//        trainerDao.save(trainer);
//        log.info("Created trainer {} ({})", username, id);
//        return trainer;
//    }
//
//    @Transactional
//    public Trainee createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
//        if (dateOfBirth == null || address == null || address.isBlank())
//            throw new IllegalArgumentException("dateOfBirth and address are required");
//
//        String username = nextUsername(firstName, lastName);
//        String password = UsernamePasswordGenerator.generatePassword();
//
//        String id = UUID.randomUUID().toString();
//        Trainee trainee = new Trainee(id, firstName, lastName, username, password, true, dateOfBirth, address);
//        traineeDao.save(trainee);
//        log.info("Created trainee {} ({})", username, id);
//        return trainee;
//    }
//
//    private String nextUsername(String firstName, String lastName) {
//        List<String> existing = userDao.findAll().stream().map(User::getUsername).toList();
//        return UsernamePasswordGenerator.generateUsername(firstName, lastName, existing);
//    }
//
//    public Trainer getTrainerByUsername(String username) {
//        return trainerDao.findByUsername(username).orElse(null);
//    }
//
//    public Trainee getTraineeByUsername(String username) {
//        return traineeDao.findByUsername(username).orElse(null);
//    }
//
//    @Transactional
//    public void updateTrainerProfile(String username, String specialization) {
//        Trainer trainer = trainerDao.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + username));
//        if (specialization == null || specialization.isBlank()) {
//            throw new IllegalArgumentException("specialization is required");
//        }
//        trainer.setSpecialization(specialization);
//        trainerDao.update(trainer);
//        log.info("Updated trainer profile {}", username);
//    }
//
//    @Transactional
//    public void updateTraineeProfile(String username, LocalDate dateOfBirth, String address) {
//        Trainee trainee = traineeDao.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + username));
//        if (dateOfBirth == null || address == null || address.isBlank())
//            throw new IllegalArgumentException("dateOfBirth and address are required");
//        trainee.setDateOfBirth(dateOfBirth);
//        trainee.setAddress(address);
//        traineeDao.update(trainee);
//        log.info("Updated trainee profile {}", username);
//    }
//
//    @Transactional
//    public void changePassword(String username, String oldPassword, String newPassword) {
//        if (!authService.authenticate(username, oldPassword))
//            throw new SecurityException("Authentication failed");
//        User user = userDao.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
//        if (newPassword == null || newPassword.isBlank())
//            throw new IllegalArgumentException("New password is empty");
//        user.setPassword(newPassword);
//        userDao.update(user);
//        log.info("Password changed for {}", username);
//    }
//
//    @Transactional
//    public void activate(String username) {
//        User user = userDao.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
//        if (user.isActive()) throw new IllegalStateException("Already active");
//        user.setActive(true);
//        userDao.update(user);
//        log.info("Activated {}", username);
//    }
//
//    @Transactional
//    public void deactivate(String username) {
//        User user = userDao.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
//        if (!user.isActive()) throw new IllegalStateException("Already inactive");
//        user.setActive(false);
//        userDao.update(user);
//        log.info("Deactivated {}", username);
//    }
//
//    @Transactional
//    public void deleteTraineeByUsername(String username) {
//        Trainee trainee = traineeDao.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + username));
//        traineeDao.delete(trainee);
//        log.warn("Deleted trainee {}", username);
//    }
//}
