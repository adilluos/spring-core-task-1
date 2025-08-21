package com.adilzhan.firsttask.dto;

import java.util.List;

public record UpdateTraineeTrainersRequest(
        List<String> trainerUsernames
) {
}
