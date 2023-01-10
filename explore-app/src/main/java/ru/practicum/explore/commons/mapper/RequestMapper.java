package ru.practicum.explore.commons.mapper;

import ru.practicum.explore.commons.dto.ParticipationRequestDto;
import ru.practicum.explore.commons.model.Request;

public class RequestMapper {

    public static ParticipationRequestDto toDto(Request model) {
        return ParticipationRequestDto.builder()
                .id(model.getId())
                .created(model.getCreatedOn())
                .eventId(model.getEvent().getId())
                .requesterId(model.getUser().getId())
                .status(model.getStatus().getName())
                .build();
    }

    public static Request toModel(ParticipationRequestDto dto) {
        return Request.builder()
                .id(dto.getId())
                .createdOn(dto.getCreated())
                .build();
    }

}
