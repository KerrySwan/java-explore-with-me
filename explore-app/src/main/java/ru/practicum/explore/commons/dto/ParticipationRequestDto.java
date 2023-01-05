package ru.practicum.explore.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ParticipationRequestDto {

    LocalDateTime created;
    long eventId;
    long id;
    long requesterId;
    String status;

}
