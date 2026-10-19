package io.daymark.task.dto.response;

import io.daymark.task.entity.TaskPriority;

import java.time.Instant;
import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDate dueDate,
        TaskPriority priority,
        boolean completed,
        Instant createdAt,
        Instant updatedAt,
        Long version) {
}
