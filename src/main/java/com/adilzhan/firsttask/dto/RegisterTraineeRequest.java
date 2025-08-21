package com.adilzhan.firsttask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RegisterTraineeRequest(
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String firstName,
        @NotBlank @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String lastName,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) LocalDate dateOfBirth,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) String address) {
}
