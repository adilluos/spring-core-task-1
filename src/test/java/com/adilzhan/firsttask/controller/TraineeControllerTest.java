package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.dto.UpdateTraineeRequest;
import com.adilzhan.firsttask.dto.UpdateTrainerRequest;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.repository.UserRepository;
import com.adilzhan.firsttask.service.web.ProfileService;
import com.adilzhan.firsttask.service.web.TrainingService;
import com.adilzhan.firsttask.service.web.security.JwtService;
import com.adilzhan.firsttask.service.web.security.TokenBlacklist;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TraineeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TraineeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ProfileService profileService;
    @MockitoBean
    private TrainingService trainingService;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklist tokenBlacklist;

    @Test
    public void getTraineeProfile() throws Exception {
        Set<Trainer> trainers = Set.of(
                new Trainer("tr1", "Ali", "Uly", "Ali.Uly", "pw", true, "YOGA"),
                new Trainer("tr2", "Ali", "Uly", "Ali.Uly.2", "pw", true, "YOGA")
        );
        Trainee trainee = new Trainee("321", "Zhan", "Bek", "Zhan.Bek", "12345", true, LocalDate.of(2000,1,1), "Abay st.");
        trainee.setTrainers(trainers);

        when(profileService.getTraineeByUsername("Zhan.Bek")).thenReturn(trainee);

        mockMvc.perform(get("/api/v1/trainee/getTraineeProfile/Zhan.Bek")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Zhan"))
                .andExpect(jsonPath("$.lastName").value("Bek"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-01-01"))
                .andExpect(jsonPath("$.isActive").value(true))
                .andExpect(jsonPath("$.trainers.length()").value(2));
    }

    @Test
    public void updateTrainee() throws Exception {
        Trainee trainee = new Trainee("321", "Zhan", "Bek", "Zhan.Bek", "12345", true, LocalDate.of(2000,2,2), "Dostyk st.");

        when(profileService.updateTraineeProfile("Zhan.Bek", LocalDate.of(2000,2,2), "Dostyk st.")).thenReturn(trainee);

        UpdateTraineeRequest request = new UpdateTraineeRequest("Zhan.Bek", LocalDate.of(2000,2,2), "Dostyk st.");
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/trainee/updateTraineeProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Zhan.Bek"))
                .andExpect(jsonPath("$.firstName").value("Zhan"))
                .andExpect(jsonPath("$.lastName").value("Bek"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-02-02"))
                .andExpect(jsonPath("$.address").value("Dostyk st."));
    }
}
