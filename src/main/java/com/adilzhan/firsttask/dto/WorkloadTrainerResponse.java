package com.adilzhan.firsttask.dto;

import java.util.Map;

public record WorkloadTrainerResponse(
        String username,
        String firstName,
        String lastName,
        boolean active,
        Map<Integer, Map<Integer, Integer>> workload
) {
}
