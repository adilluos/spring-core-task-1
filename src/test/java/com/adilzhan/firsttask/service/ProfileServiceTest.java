package com.adilzhan.firsttask.service;

import com.adilzhan.firsttask.dto.RegisterTraineeResponse;
import com.adilzhan.firsttask.dto.RegisterTrainerResponse;
import com.adilzhan.firsttask.exception.InvalidCredentialsException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        RegisterTrainerResponse result = profileService.createTrainer("Ali", "Uly", "YOGA");

        assertNotNull(result);
        assertEquals("Ali.Uly", result.username());
        assertNotNull(result.password());
    }

    @Test
    void createTrainee_GeneratesUsernameAndPassword() {
        RegisterTraineeResponse result = profileService.createTrainee("Ali", "Uly", LocalDate.of(2000,1,1), "Abay st.");

        assertNotNull(result);
        assertEquals("Ali.Uly", result.username());
        assertNotNull(result.password());
    }

    @Test
    void createTrainer_WithDuplicateUsername_GeneratesUniqueUsername() {
        List<User> existing = List.of(
                new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA"),
                new Trainer("123", "Ali", "Uly", "Ali.Uly.1", "12345", true, "YOGA")

        );

        when(userRepository.findAll()).thenReturn(existing);

        RegisterTrainerResponse result = profileService.createTrainer("Ali", "Uly", "YOGA");

        assertNotNull(result);
        assertEquals("Ali.Uly.2", result.username());
        assertNotNull(result.password());
    }

    @Test
    void createTrainee_WithDuplicateUsername_GeneratesUniqueUsername() {
        List<User> existing = List.of(
                new Trainee("123", "Ali", "Uly", "Ali.Uly", "12345", true, LocalDate.of(2000,1,1), "Abay st."),
                new Trainee("123", "Ali", "Uly", "Ali.Uly.1", "12345", true, LocalDate.of(2000,1,1), "YOGA")
        );

        when(userRepository.findAll()).thenReturn(existing);

        RegisterTraineeResponse result = profileService.createTrainee("Ali", "Uly", LocalDate.of(2000,1,1), "Abay st.");

        assertNotNull(result);
        assertEquals("Ali.Uly.2", result.username());
        assertNotNull(result.password());
    }

    @Test
    void getTrainerByUsername() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "$2a$10$fOsQo9fTgtgMGiH0tsNCn.UCGOWylLF4WCxDywTMcZO2ynmYuHuCK", true, "YOGA");

        when(trainerRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        assertEquals(new Trainer("123", "Ali", "Uly", "Ali.Uly", "$2a$10$fOsQo9fTgtgMGiH0tsNCn.UCGOWylLF4WCxDywTMcZO2ynmYuHuCK", true, "YOGA"), profileService.getTrainerByUsername("Ali.Uly"));
        assertNull(profileService.getTrainerByUsername("Not.User"));
    }

    @Test
    void getTraineeByUsername() {
        Trainee trainee = new Trainee("123", "Ali", "Uly", "Ali.Uly", "$2a$10$fOsQo9fTgtgMGiH0tsNCn.UCGOWylLF4WCxDywTMcZO2ynmYuHuCK", true, LocalDate.of(2000,1,1), "Abay st.");

        when(traineeRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainee));

        assertEquals(new Trainee("123", "Ali", "Uly", "Ali.Uly", "$2a$10$fOsQo9fTgtgMGiH0tsNCn.UCGOWylLF4WCxDywTMcZO2ynmYuHuCK", true, LocalDate.of(2000,1,1), "Abay st."), profileService.getTraineeByUsername("Ali.Uly"));
        assertNull(profileService.getTrainerByUsername("Not.User"));
    }

    @Test
    void updateTrainerProfile() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "$2a$10$fOsQo9fTgtgMGiH0tsNCn.UCGOWylLF4WCxDywTMcZO2ynmYuHuCK", true, "YOGA");

        when(trainerRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        assertEquals(new Trainer("123", "Ali", "Uly", "Ali.Uly", "$2a$10$fOsQo9fTgtgMGiH0tsNCn.UCGOWylLF4WCxDywTMcZO2ynmYuHuCK", true, "CARDIO"), profileService.updateTrainerProfile("Ali.Uly", "CARDIO"));
        //If the specialization field is null, IllegalArgumentException must be thrown
        assertThrows(IllegalArgumentException.class, () -> profileService.updateTrainerProfile("Ali.Uly", null));
    }

    @Test
    void updateTraineeProfile() {
        Trainee trainee = new Trainee("123", "Ali", "Uly", "Ali.Uly", "$2a$10$fOsQo9fTgtgMGiH0tsNCn.UCGOWylLF4WCxDywTMcZO2ynmYuHuCK", true, LocalDate.of(2000,1,1), "Abay st.");

        when(traineeRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainee));

        assertEquals(new Trainee("123", "Ali", "Uly", "Ali.Uly", "$2a$10$fOsQo9fTgtgMGiH0tsNCn.UCGOWylLF4WCxDywTMcZO2ynmYuHuCK", true, LocalDate.of(2001,2,2), "Dostyk st."), profileService.updateTraineeProfile("Ali.Uly", LocalDate.of(2001, 2,2), "Dostyk st."));
        //If the dateOfBirth field or address field is null, IllegalArgumentException must be thrown
        assertThrows(IllegalArgumentException.class, () -> profileService.updateTraineeProfile("Ali.Uly", null, ""));
    }

    @Test
    void changePassword() {
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "$2a$10$fOsQo9fTgtgMGiH0tsNCn.UCGOWylLF4WCxDywTMcZO2ynmYuHuCK", true, "YOGA");

        when(authService.authenticate("Ali.Uly", "testtest")).thenReturn(true);
        when(userRepository.findByUsername("Ali.Uly")).thenReturn(Optional.of(trainer));

        profileService.changePassword("Ali.Uly", "testtest", "newPassword");
        String newPassword = "newPassword";
        System.out.println("ENCODED: " + passwordEncoder.encode(newPassword));
        System.out.println("TRAINER: " + trainer.getPassword());


        assertTrue(passwordEncoder.matches("newPassword", trainer.getPassword()));
    }

    @Test
    void changePassword_oldPasswordIsWrong() {
        Trainer trainer = new Trainer("123", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");

        assertThrows(InvalidCredentialsException.class, () -> profileService.changePassword("Ali.Uly", "99999", "newPassword"));
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
