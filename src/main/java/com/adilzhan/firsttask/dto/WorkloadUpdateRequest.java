package com.adilzhan.firsttask.dto;

import java.time.LocalDate;

public record WorkloadUpdateRequest(
        String trainerUsername,
        String trainerFirstName,
        String trainerLastName,
        boolean active,
        LocalDate trainingDate,
        Integer trainingDuration,
        String actionType
) {
}
