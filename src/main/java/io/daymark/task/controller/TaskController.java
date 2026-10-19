package io.daymark.task.controller;

import io.daymark.common.dto.ApiResponse;
import io.daymark.common.constant.ApiMessages;
import io.daymark.task.dto.request.CreateTaskRequest;
import io.daymark.task.dto.request.UpdateTaskRequest;
import io.daymark.task.dto.response.PageResponse;
import io.daymark.task.dto.response.TaskResponse;
import io.daymark.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<TaskResponse>>> findAll(
            @RequestParam(required = false) Boolean completed,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(
                ApiMessages.TASKS_RETRIEVED,
                PageResponse.from(taskService.findAll(completed, pageable))));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<PageResponse<TaskResponse>>> findOverdue(
            @PageableDefault(size = 20, sort = "dueDate") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(
                ApiMessages.OVERDUE_TASKS_RETRIEVED,
                PageResponse.from(taskService.findOverdue(pageable))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> findById(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(ApiMessages.TASK_RETRIEVED, taskService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> create(@Valid @RequestBody CreateTaskRequest request) {
        TaskResponse created = taskService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location)
                .body(ApiResponse.success(ApiMessages.TASK_CREATED, created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> update(
            @PathVariable long id,
            @Valid @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(ApiResponse.success(ApiMessages.TASK_UPDATED, taskService.update(id, request)));
    }

    @PatchMapping("/{id}/completion")
    public ResponseEntity<ApiResponse<TaskResponse>> setCompletion(
            @PathVariable long id,
            @RequestParam boolean completed) {
        return ResponseEntity.ok(ApiResponse.success(
                ApiMessages.TASK_COMPLETION_UPDATED,
                taskService.setCompletion(id, completed)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(ApiMessages.TASK_DELETED));
    }
}
