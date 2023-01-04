package ru.practicum.explore.commons.error;

public class EntitiesNotConnectedException extends RuntimeException{
    public EntitiesNotConnectedException(String message) {
        super(message);
    }
}
