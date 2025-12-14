package com.hospital.registration.exception;

public enum ApiErrorCode {

    // === AUTH / SECURITY ===
    UNAUTHORIZED(401, "Unauthorized: token missing or invalid"),
    FORBIDDEN(403, "Forbidden: insufficient permission"),
    TOKEN_EXPIRED(401, "Unauthorized: token expired"),

    // === VALIDATION ===
    BAD_REQUEST(400, "Bad request"),
    VALIDATION_ERROR(400, "Validation failed"),

    // === RESOURCE ===
    NOT_FOUND(404, "Resource not found"),

    // === BUSINESS LOGIC ===
    BUSINESS_ERROR(501, "Business rule violation"),

    // === SYSTEM ===
    INTERNAL_ERROR(500, "Internal server error");

    private final int httpCode;
    private final String message;

    ApiErrorCode(int httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getMessage() {
        return message;
    }
}
