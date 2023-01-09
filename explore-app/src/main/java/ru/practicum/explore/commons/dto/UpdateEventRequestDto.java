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
    Long categoryId;
    String description;
    LocalDateTime eventDate;
    Long eventId;
    Boolean paid;
    Long participantLimit;
    String title;

}
