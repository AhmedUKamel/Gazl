package org.ahmedukamel.gazl.dto.api;

public record ApiResponse(
        boolean success,
        String message,
        Object data
) {
}