package it.alf.cleana.exception;

import java.time.OffsetDateTime;

public class ApiError {
    private final String message;
    private final String detail;
    private final OffsetDateTime timestamp = OffsetDateTime.now();

    public ApiError(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public String getMessage() { return message; }
    public String getDetail() { return detail; }
    public OffsetDateTime getTimestamp() { return timestamp; }
}
