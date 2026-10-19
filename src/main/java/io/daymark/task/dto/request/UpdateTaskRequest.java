package io.daymark.task.dto.request;

import io.daymark.task.entity.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateTaskRequest(
        @NotBlank @Size(max = 120) String title,
        @Size(max = 2000) String description,
        @NotNull Boolean completed,
        @FutureOrPresent LocalDate dueDate,
        @NotNull TaskPriority priority) {
}
