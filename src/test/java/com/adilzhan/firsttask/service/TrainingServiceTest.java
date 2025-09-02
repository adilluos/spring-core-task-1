package com.adilzhan.firsttask.service;

import com.adilzhan.firsttask.dto.TrainerOption;
import com.adilzhan.firsttask.dto.TrainingRow;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.model.Training;
import com.adilzhan.firsttask.model.TrainingType;
import com.adilzhan.firsttask.repository.TraineeRepository;
import com.adilzhan.firsttask.repository.TrainerRepository;
import com.adilzhan.firsttask.repository.TrainingRepository;
import com.adilzhan.firsttask.repository.TrainingTypeRepository;
import com.adilzhan.firsttask.service.web.TrainingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainingTypeRepository typeRepository;

    @InjectMocks
    private TrainingService trainingService;

    @Captor
    private ArgumentCaptor<Training> trainingCaptor;

    @Test
    void addTraining() {
        Trainer trainer = new Trainer("tr1", "Ali", "Uly", "Ali.Uly", "pw", true, "YOGA");
        Trainee trainee = new Trainee("tn1", "Zhan", "Bek", "Zhan.Bek", "pw", true, LocalDate.of(2001, 5, 17), "Abay");
        TrainingType trainingType = new TrainingType("tt1", "CARDIO", "Cardio");

        when(trainerRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));
        when(traineeRepository.findByUsername("Zhan.Bek")).thenReturn(Optional.of(trainee));
        when(typeRepository.findByCode("CARDIO")).thenReturn(Optional.of(trainingType));

        LocalDate date = LocalDate.of(2025, 9, 1);
        Integer duration = 60;
        String description = "Single";

        Training result = trainingService.addTraining("Ali.Uly", "Zhan.Bek", "CARDIO", date, duration, description);

        assertNotNull(result);
        verify(trainingRepository).save(trainingCaptor.capture());
        Training saved = trainingCaptor.getValue();

        assertEquals(trainer, saved.getTrainer());
        assertEquals(trainee, saved.getTrainee());
        assertEquals(trainingType, saved.getTrainingType());
        assertEquals(date, saved.getTrainingDate());
        assertEquals(duration, saved.getDuration());
        assertEquals(description, saved.getDescription());
        assertNotNull(saved.getId());
    }

    @Test
    void getAvailableTrainersForTrainee() {
        Trainee trainee = new Trainee("tn1", "Zhan", "Bek", "Zhan.Bek", "pw", true,
                LocalDate.of(2001, 5, 17), "Abay st.");
        when(traineeRepository.findByUsername("Zhan.Bek")).thenReturn(Optional.of(trainee));

        List<TrainerOption> options = List.of(
                new TrainerOption("Ali.Uly", "Ali", "Uly", "YOGA"),
                new TrainerOption("Bob.Fit", "Bob", "Fit", "CARDIO")
        );
        when(trainerRepository.findActiveNotAssignedTo("Zhan.Bek")).thenReturn(options);

        List<TrainerOption> result = trainingService.getAvailableTrainersForTrainee("Zhan.Bek");

        assertEquals(2, result.size());
        assertEquals("Ali.Uly", result.get(0).username());
        verify(traineeRepository).findByUsername("Zhan.Bek");
        verify(trainerRepository).findActiveNotAssignedTo("Zhan.Bek");
    }

    @Test
    void getTraineeTrainings() {
        List<TrainingRow> rows = List.of(
                new TrainingRow("Run", LocalDate.of(2025, 8, 1), "CARDIO", 30, "Ali Uly")
        );
        when(trainingRepository.findTraineeTrainings(eq("Zhan.Bek"),
                any(), any(), isNull(), isNull())).thenReturn(rows);

        List<TrainingRow> result = trainingService.getTraineeTrainings(
                "Zhan.Bek", null, null, " ", "");

        assertEquals(1, result.size());
        verify(trainingRepository).findTraineeTrainings(eq("Zhan.Bek"),
                isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void getTrainerTrainings() {
        List<TrainingRow> rows = List.of(
                new TrainingRow("Run", LocalDate.of(2025, 8, 1), "CARDIO", 30, "Zhan Bek")
        );
        when(trainingRepository.findTrainerTrainings(eq("Ali.Uly"),
                any(), any(), isNull())).thenReturn(rows);

        List<TrainingRow> result = trainingService.getTrainerTrainings(
                "Ali.Uly", null, null, "");

        assertEquals(1, result.size());
        verify(trainingRepository).findTrainerTrainings(eq("Ali.Uly"),
                isNull(), isNull(), isNull());
    }
}
