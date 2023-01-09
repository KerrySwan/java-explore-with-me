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
    Long categoryId;
    String description;
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    String title;

}
