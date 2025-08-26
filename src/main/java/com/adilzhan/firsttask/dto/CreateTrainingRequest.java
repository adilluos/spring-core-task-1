package com.adilzhan.firsttask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateTrainingRequest(
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String trainerUsername,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String traineeUsername,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String typeCode,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) LocalDate date,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Integer duration,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String description) {

}
