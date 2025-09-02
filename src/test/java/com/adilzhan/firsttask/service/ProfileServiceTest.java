package com.adilzhan.firsttask.service;

import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.model.User;
import com.adilzhan.firsttask.repository.TraineeRepository;
import com.adilzhan.firsttask.repository.TrainerRepository;
import com.adilzhan.firsttask.repository.UserRepository;
import com.adilzhan.firsttask.service.web.AuthService;
import com.adilzhan.firsttask.service.web.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private AuthService authService;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void createTrainer_GeneratesUsernameAndPassword() {
        Trainer result = profileService.createTrainer("Ali", "Uly", "YOGA");

        assertNotNull(result);
        assertEquals("Ali.Uly", result.getUsername());
        assertNotNull(result.getPassword());
    }

    @Test
    void createTrainee_WithDuplicateUsername_GeneratesUniqueUsername() {
        List<User> existing = List.of(
                new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA"),
                new Trainer("123", "Ali", "Uly", "Ali.Uly.1", "12345", true, "YOGA")

        );

        when(userRepository.findAll()).thenReturn(existing);

        Trainer result = profileService.createTrainer("Ali", "Uly", "YOGA");

        assertNotNull(result);
        assertEquals("Ali.Uly.2", result.getUsername());
        assertNotNull(result.getPassword());
    }

    @Test
    void getTrainerByUsername() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");

        when(trainerRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        assertEquals(new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA"), profileService.getTrainerByUsername("Ali.Uly"));
        assertNull(profileService.getTrainerByUsername("Not.User"));
    }

    @Test
    void updateTrainerProfile() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");

        when(trainerRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        assertEquals(new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "CARDIO"), profileService.updateTrainerProfile("Ali.Uly", "CARDIO"));
        //If the specialization field is null, IllegalArgumentException must be thrown
        assertThrows(IllegalArgumentException.class, () -> profileService.updateTrainerProfile("Ali.Uly", null));
    }

    @Test
    void changePassword() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");

        when(authService.authenticate("Ali.Uly", "12345")).thenReturn(true);
        when(userRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        profileService.changePassword("Ali.Uly", "12345", "newPassword");

        assertEquals("newPassword", trainer.getPassword());
    }

    @Test
    void changePassword_oldPasswordIsWrong() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");

        assertThrows(SecurityException.class, () -> profileService.changePassword("Ali.Uly", "99999", "newPassword"));
    }

    @Test
    void activate() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", false, "YOGA");

        when(userRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        profileService.activate("Ali.Uly");

        assertTrue(trainer.isActive());
        //check if method does not allow to activate elready active user
        assertThrows(IllegalStateException.class, () -> profileService.activate("Ali.Uly"));
    }

    @Test
    void deactivate() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");

        when(userRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        profileService.deactivate("Ali.Uly");

        assertFalse(trainer.isActive());
        //check if method does not allow to deactivate elready deactive user
        assertThrows(IllegalStateException.class, () -> profileService.deactivate("Ali.Uly"));
    }

    @Test
    void deleteTraineeByUsername() {
        Trainee trainee = new Trainee("123", "Ali", "Uly", "Ali.Uly", "12345", true, LocalDate.of(2000, 1, 1), "Abay st.");

        when(traineeRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainee));

        profileService.deleteTraineeByUsername("Ali.Uly");

        verify(traineeRepository).findByUsername("Ali.Uly");
        verify(traineeRepository).delete(trainee);
        verifyNoMoreInteractions(traineeRepository);
    }
}
