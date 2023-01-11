package ru.practicum.explore.commons.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(InvalidIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleInvalidIdException(InvalidIdException ex) {
        return new ApiError(ex, "Невалидный id", HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiError handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ApiError(ex, "Объект не найден", HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ApiError handleConstraintViolation(ConstraintViolationException ex) {
        return new ApiError(ex, "Запрос составлен с ошибкой", HttpStatus.FORBIDDEN, LocalDateTime.now());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ApiError handleInvalidIdException(ConflictException ex) {
        return new ApiError(ex, "Нарушение целостности данных", HttpStatus.FORBIDDEN, LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleBadRequestException(Exception ex) {
        return new ApiError(ex, "Запрос составлен с ошибкой", HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

}
