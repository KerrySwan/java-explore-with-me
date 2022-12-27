package ru.practicum.explore.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explore.commons.model.Location;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class EventFullDto {

    String annotation;
    CategoryDto category;
    long confirmedRequests;
    LocalDateTime createdOn;
    String description;
    LocalDateTime eventDate;
    long id;
    UserShortDto initiator;
    Location location;
    boolean paid;
    long participantLimit;
    LocalDateTime publishedOn;
    boolean requestModeration;
    String state;
    String title;
    long views;


}
