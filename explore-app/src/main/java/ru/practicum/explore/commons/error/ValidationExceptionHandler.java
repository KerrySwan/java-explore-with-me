package ru.practicum.explore.commons.error;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(InvalidIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleInvalidIdException(InvalidIdException ex) {
        return new ApiError(ex, "Невалидный id", HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleBadRequestException(MethodArgumentNotValidException ex) {
        return new ApiError(ex, "Запрос составлен с ошибкой", HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleBadRequestException(IllegalArgumentException ex) {
        return new ApiError(ex, "Запрос составлен с ошибкой", HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiError handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ApiError(ex, "Объект не найден", HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ApiError handleConstraintViolation(ConstraintViolationException ex) {
        return new ApiError(ex, "Нарушение целостности данных", HttpStatus.CONFLICT, LocalDateTime.now());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ApiError handleInvalidIdException(ConflictException ex) {
        return new ApiError(ex, "Нарушение целостности данных", HttpStatus.CONFLICT, LocalDateTime.now());
    }


}
