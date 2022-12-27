package ru.practicum.explore.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explore.commons.model.Location;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class AdminUpdateEventRequestDto {

    String annotation;
    CategoryDto category;
    String description;
    LocalDate eventDate;
    Location location;
    boolean paid;
    long participantLimit;
    boolean requestModeration;
    String title;

}
