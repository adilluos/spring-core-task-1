package com.adilzhan.firsttask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record TrainingRow(
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String trainingName,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) LocalDate trainingDate,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) String trainingType,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) Integer duration,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) String counterpartName
) {
}
