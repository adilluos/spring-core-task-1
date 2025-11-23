package com.adilzhan.firsttask.integration.database;

import com.adilzhan.firsttask.dto.RegisterTrainerResponse;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.repository.TrainerRepository;
import com.adilzhan.firsttask.service.web.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceIntegrationTest extends BaseIntegrationTest{
    @Autowired
    private ProfileService profileService;
    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    void shouldCreateTrainerInPostgres() {
        RegisterTrainerResponse created = profileService.createTrainer("Trainer", "Testuly", "CROSSFIT");

        assertNotNull(created.username());
        assertEquals("Trainer.Testuly", created.username());

        Trainer fromDb = trainerRepository.findByUsername(created.username()).orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + created.username()));
        assertEquals("Trainer", fromDb.getFirstName());
    }
}
