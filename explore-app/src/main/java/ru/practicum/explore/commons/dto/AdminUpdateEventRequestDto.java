package ru.practicum.explore.commons.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.explore.commons.model.Location;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequestDto {

    String annotation;
    CategoryDto category;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDate eventDate;
    Location location;
    boolean paid;
    long participantLimit;
    boolean requestModeration;
    String title;

}
