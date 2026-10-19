package io.daymark.task.service;

import io.daymark.task.dto.request.CreateTaskRequest;
import io.daymark.task.dto.request.UpdateTaskRequest;
import io.daymark.task.dto.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<TaskResponse> findAll(Boolean completed, Pageable pageable);

    TaskResponse findById(long id);

    TaskResponse create(CreateTaskRequest request);

    TaskResponse update(long id, UpdateTaskRequest request);

    TaskResponse setCompletion(long id, boolean completed);

    void delete(long id);
}
