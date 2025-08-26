package com.adilzhan.firsttask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record TraineesTrainingsRequest(
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String username,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) LocalDate periodFrom,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) LocalDate periodTo,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) String trainerName,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) String trainingType
) {
}
