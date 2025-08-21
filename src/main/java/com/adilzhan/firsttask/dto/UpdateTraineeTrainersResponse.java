package com.adilzhan.firsttask.dto;

import java.util.List;

public record UpdateTraineeTrainersResponse(
        List<TrainerSummary> trainers
) {
}
