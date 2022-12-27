package ru.practicum.explore.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class UpdateEventRequestDto {

    String annotation;
    long categoryId;
    String description;
    LocalDateTime eventDate;
    long eventId;
    boolean paid;
    long participantLimit;
    String title;

}
