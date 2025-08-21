package com.adilzhan.firsttask.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record TraineeProfileResponse(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String address,
        boolean isActive,
        List<TrainerSummary> trainers) {
}

