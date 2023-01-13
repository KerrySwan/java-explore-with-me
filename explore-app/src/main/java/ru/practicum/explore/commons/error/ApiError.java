package ru.practicum.explore.commons.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ApiError {

    String message;
    String reason;
    HttpStatus status;
    LocalDateTime timestamp;
    List<String> errors;

    public ApiError(Exception ex, String message, HttpStatus status, LocalDateTime timestamp) {
        this.errors =
                Arrays.stream(ex.getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.toList());
        this.message = message;
        this.reason = ex.getMessage();
        this.status = status;
        this.timestamp = timestamp;
    }
}
