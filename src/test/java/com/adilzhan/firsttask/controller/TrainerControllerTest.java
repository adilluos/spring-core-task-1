package com.adilzhan.firsttask.controller;

import com.adilzhan.firsttask.dto.TraineeSummary;
import com.adilzhan.firsttask.dto.UpdateTrainerRequest;
import com.adilzhan.firsttask.model.Trainee;
import com.adilzhan.firsttask.model.Trainer;
import com.adilzhan.firsttask.repository.UserRepository;
import com.adilzhan.firsttask.service.web.ProfileService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrainerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ProfileService profileService;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private TokenBlacklist tokenBlacklist;

    @Test
    public void getTrainerProfile() throws Exception {
        Set<Trainee> trainees = Set.of(
                new Trainee("tn0", "Zhan", "Bek", "Zhan.Bek", "pw", true,
                        LocalDate.of(2001, 5, 17), "Abay st."),
                new Trainee("tn1", "Zhan", "Bek", "Zhan.Bek.1", "pw", true,
                        LocalDate.of(2001, 5, 18), "Dostyk st.")
        );
        Trainer trainer = new Trainer("321", "Ali", "Uly", "Ali.Uly", "12345", true, "YOGA");
        trainer.setTrainees(trainees);

        when(profileService.getTrainerByUsername("Ali.Uly")).thenReturn(trainer);

        mockMvc.perform(get("/api/v1/trainer/getTrainerProfile/Ali.Uly")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ali"))
                .andExpect(jsonPath("$.lastName").value("Uly"))
                .andExpect(jsonPath("$.specialization").value("YOGA"))
                .andExpect(jsonPath("$.isActive").value(true))
                .andExpect(jsonPath("$.trainees[0].username").value("Zhan.Bek"))
                .andExpect(jsonPath("$.trainees[0].firstName").value("Zhan"))
                .andExpect(jsonPath("$.trainees[0].lastName").value("Bek"))
                .andExpect(jsonPath("$.trainees[1].username").value("Zhan.Bek.1"))
                .andExpect(jsonPath("$.trainees.length()").value(2));
    }

    @Test
    public void updateTrainer() throws Exception {
        Trainer trainer = new Trainer("321", "Ali", "Uly", "Ali.Uly", "12345", true, "CROSSFIT");

        when(profileService.updateTrainerProfile("Ali.Uly", "CROSSFIT")).thenReturn(trainer);

        UpdateTrainerRequest request = new UpdateTrainerRequest("Ali.Uly", "CROSSFIT");
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/trainer/updateTrainerProfile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Ali.Uly"))
                .andExpect(jsonPath("$.firstName").value("Ali"))
                .andExpect(jsonPath("$.lastName").value("Uly"))
                .andExpect(jsonPath("$.specialization").value("CROSSFIT"))
                .andExpect(jsonPath("$.isActive").value(true));
    }
}
