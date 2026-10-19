package io.daymark.task.mapper;

import io.daymark.task.dto.request.CreateTaskRequest;
import io.daymark.task.dto.request.UpdateTaskRequest;
import io.daymark.task.dto.response.TaskResponse;
import io.daymark.task.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskEntity toEntity(CreateTaskRequest request) {
        return new TaskEntity(
                request.title().trim(),
                normalizeDescription(request.description()),
                false);
    }

    public void updateEntity(TaskEntity entity, UpdateTaskRequest request) {
        entity.setTitle(request.title().trim());
        entity.setDescription(normalizeDescription(request.description()));
        entity.setCompleted(request.completed());
    }

    public TaskResponse toResponse(TaskEntity entity) {
        return new TaskResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.isCompleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getVersion());
    }

    private String normalizeDescription(String description) {
        return description == null ? "" : description.trim();
    }
}
