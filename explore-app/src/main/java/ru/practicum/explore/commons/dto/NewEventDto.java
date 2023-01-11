package ru.practicum.explore.commons.dto;

import lombok.*;
import ru.practicum.explore.commons.model.Location;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {

    String annotation;
    Long category;
    String description;
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    String title;

}
