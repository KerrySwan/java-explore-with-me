package ru.practicum.explore.service;

import ru.practicum.explore.commons.dto.*;

import java.util.List;

public interface EventService {

    public List<EventShortDto> getEventsByUser(long userId, int from, int size);

    public EventFullDto updateEventByUser(long userId, UpdateEventRequestDto dto);

    public EventFullDto addEventByUser(long userId, NewEventDto dto);

    public EventFullDto getEventByUser(long userId, long eventId);

    public EventFullDto cancelEventByUser(long userId, long eventId);

    public List<ParticipationRequestDto> getRequestsByUser(long userId, long eventId);

    public ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId);

    public ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId);

}
