package io.daymark.task.controller;

import io.daymark.common.constant.ApiMessages;
import io.daymark.common.dto.ApiResponse;
import io.daymark.task.dto.request.CreateTaskRequest;
import io.daymark.task.dto.request.UpdateTaskRequest;
import io.daymark.task.dto.response.TaskResponse;
import io.daymark.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private static final int MAX_SEARCH_LENGTH = 100;
    private static final Set<String> SORTABLE_TASK_FIELDS = Set.of(
            "id", "title", "description", "dueDate", "priority", "completed", "createdAt", "updatedAt");

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TaskResponse>>> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean completed,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        if (hasUnsupportedSortField(pageable)) {
            return invalidSortResponse();
        }
        if (search != null && search.length() > MAX_SEARCH_LENGTH) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(
                    ApiMessages.VALIDATION_FAILED_CODE,
                    ApiMessages.VALIDATION_FAILED,
                    Map.of("findAll.search", ApiMessages.SEARCH_TOO_LONG)));
        }
        return ResponseEntity.ok(ApiResponse.success(
                "Tasks retrieved successfully",
                taskService.findAll(search, completed, pageable)));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<Page<TaskResponse>>> findOverdue(
            @PageableDefault(size = 20, sort = {"dueDate", "id"}) Pageable pageable) {
        if (hasUnsupportedSortField(pageable)) {
            return invalidSortResponse();
        }
        return ResponseEntity.ok(ApiResponse.success("Overdue tasks retrieved successfully", taskService.findOverdue(pageable)));
    }

    private boolean hasUnsupportedSortField(Pageable pageable) {
        return pageable.getSort().stream()
                .map(Sort.Order::getProperty)
                .anyMatch(property -> !SORTABLE_TASK_FIELDS.contains(property));
    }

    private ResponseEntity<ApiResponse<Page<TaskResponse>>> invalidSortResponse() {
        return ResponseEntity.badRequest().body(ApiResponse.failure(
                ApiMessages.VALIDATION_FAILED_CODE,
                ApiMessages.VALIDATION_FAILED,
                Map.of("pageable.sort", ApiMessages.UNSUPPORTED_SORT_FIELD)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> findById(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success("Task retrieved successfully", taskService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> create(@Valid @RequestBody CreateTaskRequest request) {
        TaskResponse created = taskService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location)
                .body(ApiResponse.success("Task created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> update(
            @PathVariable long id,
            @Valid @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Task updated successfully", taskService.update(id, request)));
    }

    @PatchMapping("/{id}/completion")
    public ResponseEntity<ApiResponse<TaskResponse>> setCompletion(
            @PathVariable long id,
            @RequestParam boolean completed) {
        return ResponseEntity.ok(ApiResponse.success(
                "Task completion state updated successfully",
                taskService.setCompletion(id, completed)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Task deleted successfully"));
    }
}
