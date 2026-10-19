package io.daymark.task.service.impl;

import io.daymark.common.exception.ResourceNotFoundException;
import io.daymark.task.dto.request.CreateTaskRequest;
import io.daymark.task.dto.request.UpdateTaskRequest;
import io.daymark.task.dto.response.TaskResponse;
import io.daymark.task.entity.TaskEntity;
import io.daymark.task.mapper.TaskMapper;
import io.daymark.task.repository.TaskRepository;
import io.daymark.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final Clock clock;

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponse> findAll(String search, Boolean completed, Pageable pageable) {
        Page<TaskEntity> tasks = taskRepository.searchTasks(normalizeSearch(search), completed, pageable);
        return tasks.map(taskMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponse> findOverdue(Pageable pageable) {
        return taskRepository.findByDueDateBeforeAndCompletedFalse(LocalDate.now(clock), pageable)
                .map(taskMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse findById(long id) {
        return taskMapper.toResponse(getTask(id));
    }

    @Override
    public TaskResponse create(CreateTaskRequest request) {
        TaskEntity created = taskRepository.save(taskMapper.toEntity(request));
        return taskMapper.toResponse(created);
    }

    @Override
    public TaskResponse update(long id, UpdateTaskRequest request) {
        TaskEntity task = getTask(id);
        taskMapper.updateEntity(task, request);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponse setCompletion(long id, boolean completed) {
        TaskEntity task = getTask(id);
        task.setCompleted(completed);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    public void delete(long id) {
        TaskEntity task = getTask(id);
        taskRepository.delete(task);
    }

    private TaskEntity getTask(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
    }

    private String normalizeSearch(String search) {
        if (search == null || search.isBlank()) {
            return null;
        }
        return search.trim()
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_");
    }
}
