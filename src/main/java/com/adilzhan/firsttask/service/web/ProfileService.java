package com.adilzhan.firsttask.service.web;

import com.adilzhan.firsttask.dto.RegisterTraineeResponse;
import com.adilzhan.firsttask.dto.RegisterTrainerResponse;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.model.User;
import com.adilzhan.firsttask.repository.TraineeRepository;
import com.adilzhan.firsttask.repository.TrainerRepository;
import com.adilzhan.firsttask.repository.UserRepository;
import com.adilzhan.firsttask.util.UsernamePasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {
    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final AuthService authService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public ProfileService(UserRepository userRepository, TraineeRepository traineeRepository, TrainerRepository trainerRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.authService = authService;
    }

    @Transactional
    public RegisterTrainerResponse createTrainer(String firstName, String lastName, String specialization) {
        String username = nextUsername(firstName, lastName);
        String password = UsernamePasswordGenerator.generatePassword();
        String hashed = passwordEncoder.encode(password);
        String id = UUID.randomUUID().toString();

        Trainer trainer = new Trainer(id, firstName, lastName, username, hashed, true, specialization);
        trainerRepository.save(trainer);
        log.info("Created trainer {} ({})", username, id);
        return new RegisterTrainerResponse(username, password);
    }

    @Transactional
    public RegisterTraineeResponse createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
//        if (dateOfBirth == null || address == null || address.isBlank())
//            throw new IllegalArgumentException("dateOfBirth and address are required");

        String username = nextUsername(firstName, lastName);
        String password = UsernamePasswordGenerator.generatePassword();
        String hashed = passwordEncoder.encode(password);
        String id = UUID.randomUUID().toString();

        Trainee trainee = new Trainee(id, firstName, lastName, username, hashed, true, dateOfBirth, address);
        traineeRepository.save(trainee);
        log.info("Created trainee {} ({}), {}", username, id, password);
        return new RegisterTraineeResponse(username, password);
    }

    public String nextUsername(String firstName, String lastName) {
        List<String> existing = userRepository.findAll().stream().map(User::getUsername).toList();
        return UsernamePasswordGenerator.generateUsername(firstName, lastName, existing);
    }

    public Trainer getTrainerByUsername(String username) {
        return trainerRepository.findByUsername(username).orElse(null);
    }

    public Trainee getTraineeByUsername(String username) {
        return traineeRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public Trainer updateTrainerProfile(String username, String specialization) {
        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + username));
        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("specialization is required");
        }
        trainer.setSpecialization(specialization);
        log.info("Updated trainer profile {}", username);
        return trainer;
    }

    @Transactional
    public Trainee updateTraineeProfile(String username, LocalDate dateOfBirth, String address) {
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + username));
        if (dateOfBirth == null || address == null || address.isBlank())
            throw new IllegalArgumentException("dateOfBirth and address are required");
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);
        log.info("Updated trainee profile {}", username);
        return trainee;
    }

    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        if (!authService.authenticate(username, oldPassword))
            throw new SecurityException("Authentication failed");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        if (newPassword == null || newPassword.isBlank())
            throw new IllegalArgumentException("New password is empty");
        String hashed = passwordEncoder.encode(newPassword);
        user.setPassword(hashed);
        log.info("Password changed for {}", username);
    }

    @Transactional
    public void activate(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        if (user.isActive()) throw new IllegalStateException("Already active");
        user.setActive(true);
        log.info("Activated {}", username);
    }

    @Transactional
    public void deactivate(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        if (!user.isActive()) throw new IllegalStateException("Already inactive");
        user.setActive(false);
        log.info("Deactivated {}", username);
    }

    @Transactional
    public void deleteTraineeByUsername(String username) {
        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + username));
        traineeRepository.delete(trainee);
        log.warn("Deleted trainee {}", username);
    }
}
