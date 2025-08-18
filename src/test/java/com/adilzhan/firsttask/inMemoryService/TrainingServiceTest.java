//package com.adilzhan.firsttask.service;
//
//import com.adilzhan.firsttask.dao.inMemoryDao.TrainingDao;
//import com.adilzhan.firsttask.model.Training;
//import com.adilzhan.firsttask.model.TrainingTypeEnum;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class TrainingServiceTest {
//
//    private TrainingDao trainingDao;
//    private TrainingService trainingService;
//
//    @BeforeEach
//    void setUp() {
//        trainingDao = mock(TrainingDao.class);
//        trainingService = new TrainingService(trainingDao);
//    }
//
//    @Test
//    void testCreateTraining_StoresTrainingCorrectly() {
//        String trainerId = "trainer123";
//        String traineeId = "trainee123";
//        String trainingName = "Full body";
//        TrainingTypeEnum trainingTypeEnum = TrainingTypeEnum.STRENGTH;
//
//        Training training = trainingService.createTraining(trainerId, traineeId, trainingName, trainingTypeEnum, LocalDate.of(2025,7,7), Duration.ofMinutes(40));
//
//        assertNotNull(training);
//        assertEquals(trainerId, training.getTrainerUserId());
//        assertEquals(traineeId, training.getTraineeUserId());
//        assertEquals(trainingName, training.getTrainingName());
//        assertEquals(trainingTypeEnum, training.getTrainingType());
//
//        verify(trainingDao).save(any(Training.class));
//    }
//
//    @Test
//    void testGetTrainingById_ReturnsTraining() {
//        Training mockTraining = new Training("training101", "trainer124", "trainee124", "Mega Body", TrainingTypeEnum.BODYBUILDING, LocalDate.of(2025,7,7), Duration.ofMinutes(40));
//
//        when(trainingDao.findById("training101")).thenReturn(Optional.of(mockTraining));
//
//        Training result = trainingService.getTrainingById("training101");
//
//        assertNotNull(result);
//        assertEquals("training101", result.getId());
//        assertEquals(TrainingTypeEnum.BODYBUILDING, result.getTrainingType());
//    }
//
//    @Test
//    void testGetTrainingById_ReturnsNullIfNotFound() {
//        when(trainingDao.findById("notfound")).thenReturn(Optional.empty());
//
//        Training result = trainingService.getTrainingById("notfound");
//
//        assertNull(result);
//    }
//
//    @Test
//    void testGetAllTrainings_ReturnsList() {
//        List<Training> mockList = List.of(
//                new Training(),
//                new Training()
//        );
//
//        when(trainingDao.findAll()).thenReturn(mockList);
//
//        List<Training> result = trainingService.getAllTrainings();
//
//        assertEquals(2, result.size());
//    }
//}
