package ru.practicum.explore.service;

import ru.practicum.explore.commons.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    List<ParticipationRequestDto> getRequestsByUserId(long userId);

    ParticipationRequestDto addParticipationRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequestByUser(long userId, long requestId);

}
