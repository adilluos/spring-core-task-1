package com.adilzhan.firsttask.dto;

import java.util.List;

public record TrainerProfileResponse (
        String firstName,
        String lastName,
        String specialization,
        boolean isActive,
        List<TraineeSummary> trainees
){
}
