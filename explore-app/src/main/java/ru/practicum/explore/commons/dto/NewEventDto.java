package ru.practicum.explore.commons.dto;

import lombok.*;
import ru.practicum.explore.commons.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotBlank
    String annotation;
    Long category;
    @NotBlank
    String description;
    @NotNull
    LocalDateTime eventDate;
    @NotNull
    Location location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    @NotBlank
    String title;

}
