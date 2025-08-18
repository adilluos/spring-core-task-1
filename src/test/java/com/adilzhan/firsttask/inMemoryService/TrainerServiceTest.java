//package com.adilzhan.firsttask.service;
//
//import com.adilzhan.firsttask.dao.inMemoryDao.TrainerDao;
//import com.adilzhan.firsttask.model.Trainer;
//import com.adilzhan.firsttask.service.inMemory.TrainerService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class TrainerServiceTest {
//
//    private TrainerDao trainerDao;
//    private TrainerService trainerService;
//
//    @BeforeEach
//    void setUp() {
//        trainerDao = mock(TrainerDao.class);
//        trainerService = new TrainerService(trainerDao);
//    }
//
//    @Test
//    void testCreateTrainer_GeneratesUsernameAndPassword() {
//        when(trainerDao.findAll()).thenReturn(List.of());
//
//        Trainer trainer = trainerService.createTrainer("Ali", "Bolat", "Yoga");
//
//        assertNotNull(trainer);
//        assertEquals("Ali.Bolat", trainer.getUsername());
//        assertEquals("Yoga", trainer.getSpecialization());
//        assertNotNull(trainer.getPassword());
//
//        verify(trainerDao).save(any(Trainer.class));
//    }
//
//    @Test
//    void testCreateTrainer_WithDuplicateUsername_GeneratesUniqueUsername() {
//        List<Trainer> existing = List.of(
//                new Trainer("Ali", "Bolat", "Ali.Bolat", "pass12310", true, "Yoga", UUID.randomUUID().toString()),
//                new Trainer("Ali", "Bolat", "Ali.Bolat.1", "pass12310", true, "Yoga", UUID.randomUUID().toString())
//        );
//
//        when(trainerDao.findAll()).thenReturn(existing);
//
//        Trainer newTrainer = trainerService.createTrainer("Ali", "Bolat", "Yoga");
//
//        assertEquals("Ali.Bolat.2", newTrainer.getUsername());
//        verify(trainerDao).save(any(Trainer.class));
//    }
//
//    @Test
//    void testUpdateTrainer_DelegatesToDao() {
//        Trainer trainer = new Trainer("Madi", "Serik", "Madi.Serik", "pass12310", true, "CrossFit", "trainer123");
//
//        trainerService.updateTrainer(trainer);
//
//        verify(trainerDao).update(trainer);
//    }
//
//    @Test
//    void testGetTrainerById_ReturnsCorrectTrainer() {
//        Trainer trainer = new Trainer("Madi", "Serik", "Madi.Serik", "pass12310", true, "CrossFit", "trainer123");
//
//        when(trainerDao.findById("trainer123")).thenReturn(java.util.Optional.of(trainer));
//
//        Trainer result = trainerService.getTrainerById("trainer123");
//
//        assertNotNull(result);
//        assertEquals("trainer123", result.getId());
//    }
//
//    @Test
//    void testGetTrainerById_ReturnsNullIfNotFound() {
//        when(trainerDao.findById("notfound")).thenReturn(java.util.Optional.empty());
//
//        Trainer result = trainerService.getTrainerById("notfound");
//
//        assertNull(result);
//    }
//
//    @Test
//    void testGetAllTrainers_ReturnsList() {
//        when(trainerDao.findAll()).thenReturn(List.of(new Trainer(), new Trainer()));
//
//        List<Trainer> trainers = trainerService.getAllTrainers();
//
//        assertEquals(2, trainers.size());
//    }
//
//}
