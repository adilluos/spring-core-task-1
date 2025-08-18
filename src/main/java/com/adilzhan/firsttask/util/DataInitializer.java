//package com.adilzhan.firsttask.util;
//
//import com.adilzhan.firsttask.model.Trainee;
//import com.adilzhan.firsttask.model.Trainer;
//import com.adilzhan.firsttask.storage.InMemoryStorage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Component
//public class DataInitializer implements BeanPostProcessor {
//
//    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
//
//    @Value("${trainee.data.path}")
//    private String traineeFilePath;
//
//    @Value("${trainer.data.path}")
//    private String trainerFilePath;
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (bean instanceof InMemoryStorage storage) {
//            log.info("Initializing in-memory storage from files...");
//            loadTrainees(storage);
//            loadTrainers(storage);
//        }
//        return bean;
//    }
//
//    private void loadTrainees(InMemoryStorage storage) {
//        int count = 0;
//        try (BufferedReader reader = new BufferedReader(new FileReader(traineeFilePath))){
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 4) {
//                    String firstName = parts[0];
//                    String lastName = parts[1];
//                    LocalDate dateOfBirth = LocalDate.parse(parts[2]);
//                    String address = parts[3];
//
//                    String username = firstName + "." + lastName;
//                    String password = UsernamePasswordGenerator.generatePassword();
//                    String userId = UUID.randomUUID().toString();
//
//                    Trainee trainee = new Trainee(firstName, lastName, username, password, true, dateOfBirth, userId, address);
//                    storage.getTraineeMap().put(userId, trainee);
//                    count++;
//                }
//            }
//            log.info("Loaded {} trainees from file: {}", count, traineeFilePath);
//        } catch (Exception e) {
//            log.error("Failed to load trainees from file {}: {}", traineeFilePath, e.getMessage());
//        }
//    }
//
//    private void loadTrainers(InMemoryStorage storage) {
//        int count = 0;
//        try (BufferedReader reader = new BufferedReader(new FileReader(trainerFilePath))){
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 3) {
//                    String firstName = parts[0];
//                    String lastName = parts[1];
//                    String specialization = parts[2];
//
//                    String username = firstName + "." + lastName;
//                    String password = UsernamePasswordGenerator.generatePassword();
//                    String userId = UUID.randomUUID().toString();
//
//                    Trainer trainer = new Trainer(firstName, lastName, username, password, true, specialization, userId);
//                    storage.getTrainerMap().put(userId, trainer);
//                    count++;
//                }
//            }
//            log.info("Loaded {} trainers from file: {}", count, trainerFilePath);
//        } catch (Exception e) {
//            log.error("Failed to load trainers from file {}: {}", trainerFilePath, e.getMessage());
//        }
//    }
//}
