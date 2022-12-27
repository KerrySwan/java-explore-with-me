package ru.practicum.explore.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explore.commons.model.Location;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class NewEventDto {

    String annotation;
    long categoryId;
    String description;
    LocalDateTime eventDate;
    Location location;
    boolean paid;
    long participantLimit;
    boolean requestModeration;
    String title;

}
