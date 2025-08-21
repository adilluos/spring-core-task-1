package com.adilzhan.firsttask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UpdateTrainerRequest(
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String username,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String firstName,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String lastName,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String specialization,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean isActive
) {
}
