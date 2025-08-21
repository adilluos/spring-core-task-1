package com.adilzhan.firsttask.dto;

import java.util.List;

public record UpdateTrainerResponse(
        String username,
        String firstName,
        String lastName,
        String specialization,
        boolean isActive,
        List<TraineeSummary> trainees) {
}
