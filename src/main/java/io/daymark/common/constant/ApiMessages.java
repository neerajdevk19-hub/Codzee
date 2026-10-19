package io.daymark.common.constant;

/** Centralized API response codes and user-facing messages. */
public final class ApiMessages {
    private ApiMessages() {
    }

    public static final String SUCCESS_CODE = "SUCCESS";
    public static final String RESOURCE_NOT_FOUND_CODE = "RESOURCE_NOT_FOUND";
    public static final String ENDPOINT_NOT_FOUND_CODE = "ENDPOINT_NOT_FOUND";
    public static final String VALIDATION_FAILED_CODE = "VALIDATION_FAILED";
    public static final String INVALID_REQUEST_CODE = "INVALID_REQUEST";
    public static final String METHOD_NOT_ALLOWED_CODE = "METHOD_NOT_ALLOWED";
    public static final String UNSUPPORTED_MEDIA_TYPE_CODE = "UNSUPPORTED_MEDIA_TYPE";
    public static final String RESOURCE_CONFLICT_CODE = "RESOURCE_CONFLICT";
    public static final String INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR";

    public static final String TASKS_RETRIEVED = "Tasks retrieved successfully";
    public static final String OVERDUE_TASKS_RETRIEVED = "Overdue tasks retrieved successfully";
    public static final String TASK_RETRIEVED = "Task retrieved successfully";
    public static final String TASK_CREATED = "Task created successfully";
    public static final String TASK_UPDATED = "Task updated successfully";
    public static final String TASK_COMPLETION_UPDATED = "Task completion state updated successfully";
    public static final String TASK_DELETED = "Task deleted successfully";

    public static final String ENDPOINT_NOT_FOUND = "The requested endpoint does not exist";
    public static final String INVALID_FIELD_VALUE = "Invalid value";
    public static final String VALIDATION_FAILED = "Request validation failed";
    public static final String SEARCH_TOO_LONG = "Search must be at most 100 characters";
    public static final String UNSUPPORTED_SORT_FIELD = "Sort field is not supported";
    public static final String INVALID_REQUEST = "The request is malformed or contains invalid parameters";
    public static final String METHOD_NOT_ALLOWED = "The HTTP method is not supported for this endpoint";
    public static final String UNSUPPORTED_MEDIA_TYPE = "The request content type is not supported";
    public static final String RESOURCE_CONFLICT = "The request conflicts with the current resource state";
    public static final String INTERNAL_SERVER_ERROR = "An unexpected error occurred";
    public static final String UNHANDLED_EXCEPTION_LOG = "Unhandled exception while processing request";
    public static final String RESOURCE_NOT_FOUND_TEMPLATE = "%s with id %s was not found";
}
