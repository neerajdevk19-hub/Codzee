package io.daymark.common.exception;

import io.daymark.common.constant.ApiMessages;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Object id) {
        super(ApiMessages.RESOURCE_NOT_FOUND_TEMPLATE.formatted(resource, id));
    }
}
