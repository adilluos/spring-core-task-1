package com.adilzhan.firsttask.service;

import com.adilzhan.firsttask.dao.TraineeDao;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraineeServiceTest {

    private TraineeDao traineeDao;
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        traineeDao = mock(TraineeDao.class);
        traineeService = new TraineeService(traineeDao);
    }

    @Test
    void testCreateTrainee_GeneratesUsernameAndPassword() {
        when(traineeDao.findAll()).thenReturn(new ArrayList<>());

        Trainee result = traineeService.createTrainee(
                "Arman", "Babakov", LocalDate.of(2001, 11, 13), "Astana"
        );

        assertNotNull(result);
        assertEquals("Arman.Babakov", result.getUsername());
        assertNotNull(result.getPassword());
        verify(traineeDao).save(any(Trainee.class));
    }

    @Test
    void testCreateTrainee_WithDuplicateUsername_GeneratesUniqueUsername() {
        List<Trainee> existing = List.of(
                new Trainee("Arman", "Babakov", "Arman.Babakov", "pass12310", true, LocalDate.of(2000, 12, 12), "Astana", "id1"),
                new Trainee("Arman", "Babakov", "Arman.Babakov.1", "pass12310", true, LocalDate.of(2000, 12, 12), "Astana", "id2")
        );

        when(traineeDao.findAll()).thenReturn(existing);

        Trainee newTrainee = traineeService.createTrainee(
                "Arman", "Babakov", LocalDate.of(2000, 12, 12), "Astana"
        );
        assertEquals("Arman.Babakov.2", newTrainee.getUsername());
        verify(traineeDao).save(any(Trainee.class));
    }

    @Test
    void testUpdateTrainee_DelegatesToDao() {
        Trainee trainee = new Trainee("Arman", "Babakov", "Arman.Babakov", "pass12310", true, LocalDate.of(2000, 12, 12), "Astana", "idid");

        traineeService.updateTrainee(trainee);

        verify(traineeDao).update(trainee);
    }

    @Test
    void testDeleteTrainee_DelegatesToDao() {
        traineeService.deleteTrainee("idid");
        verify(traineeDao).delete("idid");
    }

    @Test
    void testGetTraineeById_ReturnsCorrectValue() {
        Trainee trainee = new Trainee("Arman", "Babakov", "Arman.Babakov", "pass12310", true, LocalDate.of(2000, 12, 12), "Astana", "idid");

        when(traineeDao.findById("idid")).thenReturn(java.util.Optional.of(trainee));

        Trainee result = traineeService.getTraineeById("idid");

        assertNotNull(result);
        assertEquals("idid", result.getUserId());
    }

    @Test
    void testGetTraineeById_ReturnsNullIfNotFound() {
        when(traineeDao.findById("notfound")).thenReturn(java.util.Optional.empty());

        Trainee result = traineeService.getTraineeById("notfound");

        assertNull(result);
    }

    @Test
    void testAllTrainees_ReturnList() {
        List<Trainee> mockList = List.of(new Trainee(), new Trainee());

        when(traineeDao.findAll()).thenReturn(mockList);

        List<Trainee> result = traineeService.getAllTrainees();

        assertEquals(2, result.size());
    }
}
