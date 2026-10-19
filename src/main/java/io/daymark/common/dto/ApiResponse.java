package io.daymark.common.dto;

import io.daymark.common.constant.ApiMessages;
import java.time.Instant;
import java.util.Map;

public record ApiResponse<T>(
        boolean success,
        String code,
        String message,
        T data,
        Map<String, String> errors,
        Instant timestamp) {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, ApiMessages.SUCCESS_CODE, message, data, Map.of(), Instant.now());
    }

    public static ApiResponse<Void> success(String message) {
        return success(message, null);
    }

    public static <T> ApiResponse<T> failure(String code, String message) {
        return failure(code, message, Map.of());
    }

    public static <T> ApiResponse<T> failure(String code, String message, Map<String, String> errors) {
        return new ApiResponse<>(false, code, message, null, Map.copyOf(errors), Instant.now());
    }
}
