package io.daymark.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
        @NotBlank @Size(max = 120) String title,
        @Size(max = 2000) String description,
        @NotNull Boolean completed) {
}
