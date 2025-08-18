package com.adilzhan.firsttask.hibernateService;

import com.adilzhan.firsttask.dao.hibernateDao.TraineeDao;
import com.adilzhan.firsttask.dao.hibernateDao.TrainerDao;
import com.adilzhan.firsttask.dao.hibernateDao.TrainingDao;
import com.adilzhan.firsttask.dao.hibernateDao.TrainingTypeDao;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.model.Training;
import com.adilzhan.firsttask.model.TrainingType;
import com.adilzhan.firsttask.service.hibernate.TrainingService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Test
    public void addTraining_persistsWithResolvedRefs() {
        TrainingDao trainingDao = mock(TrainingDao.class);
        TrainerDao trainerDao = mock(TrainerDao.class);
        TraineeDao traineeDao = mock(TraineeDao.class);
        TrainingTypeDao trainingTypeDao = mock(TrainingTypeDao.class);

        TrainingService service = new TrainingService(trainerDao, traineeDao, trainingDao, trainingTypeDao);

        Trainer trainer = new Trainer(
                UUID.randomUUID().toString(), "Ali", "Bolat", "ali",
                "p", true, "Yoga"
        );
        Trainee trainee = new Trainee(
                UUID.randomUUID().toString(), "John", "Doe", "john",
                "p", true, LocalDate.of(2000,1,1), "Almaty"
        );
        TrainingType type = new TrainingType(UUID.randomUUID().toString(),"YOGA","Yoga");

        when(trainerDao.findByUsername("ali")).thenReturn(Optional.of(trainer));
        when(traineeDao.findByUsername("john")).thenReturn(Optional.of(trainee));
        when(trainingTypeDao.findByCode("YOGA")).thenReturn(Optional.of(type));

        Training t = service.addTraining("ali","john","YOGA", LocalDate.now(), 60, "Session");

        assertNotNull(t.getId());
        assertEquals(type, t.getTrainingType());
        verify(trainingDao).save(any(Training.class));
    }

//    @Mock
//    TrainingDao trainingDao;
//    @Mock
//    TrainerDao trainerDao;
//    @Mock
//    TraineeDao traineeDao;
//    @Mock
//    TrainingTypeDao trainingTypeDao;
//
//    @Autowired
//    TrainingService service;
//
//    @BeforeEach
//    void init() {
//        MockitoAnnotations.openMocks(this);  // initializes @Mock fields
//        service = new TrainingService(trainerDao, traineeDao, trainingDao, trainingTypeDao);
//    }
//
//    @Test
//    public void addTraining_persistsWithResolvedRefs() {
//        Trainer trainer = new Trainer("t1", "Ali", "Bolat", "ali", "p", true, "Yoga");
//        Trainee trainee = new Trainee("u1", "John", "Doe", "john", "p", true, LocalDate.of(2000,1,1), "Almaty");
//        TrainingType type = new TrainingType("tt1","YOGA","Yoga");
//
//        when(trainerDao.findByUsername("ali")).thenReturn(Optional.of(trainer));
//        when(traineeDao.findByUsername("john")).thenReturn(Optional.of(trainee));
//        when(trainingTypeDao.findByCode("YOGA")).thenReturn(Optional.of(type));
//
//        Training t = service.addTraining("ali","john","YOGA", LocalDate.now(), 60, "Session");
//
//        assertNotNull(t.getId());
//        assertEquals(type, t.getTrainingType());
//        verify(trainingDao).save(any(Training.class));
//    }
}

