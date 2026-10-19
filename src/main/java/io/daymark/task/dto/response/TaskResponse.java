package io.daymark.task.dto.response;

import java.time.Instant;

public record TaskResponse(
        Long id,
        String title,
        String description,
        boolean completed,
        Instant createdAt,
        Instant updatedAt,
        Long version) {
}
