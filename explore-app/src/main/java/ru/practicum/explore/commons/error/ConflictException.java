package ru.practicum.explore.commons.error;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
