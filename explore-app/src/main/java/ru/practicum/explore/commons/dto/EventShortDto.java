package ru.practicum.explore.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class EventShortDto {

    String annotation;
    CategoryDto category;
    long confirmedRequests;
    LocalDateTime eventDate;
    long id;
    UserShortDto initiator;
    boolean paid;
    String title;
    @Builder.Default
    private Long views = 0L;

}
