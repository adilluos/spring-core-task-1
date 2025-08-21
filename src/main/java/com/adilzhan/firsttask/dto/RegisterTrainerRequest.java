package com.adilzhan.firsttask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RegisterTrainerRequest(
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String firstName,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String lastName,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String specialization) {
}
