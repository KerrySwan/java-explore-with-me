package ru.practicum.explore.commons.error;

public class ConditionsNotFulfilledException extends RuntimeException{
    public ConditionsNotFulfilledException(String message) {
        super(message);
    }
}
