package ru.practicum.explore.service;

import ru.practicum.explore.commons.dto.*;

import java.util.List;

public interface EventService {

    /**
     * Public: События
     **/

    public List<EventShortDto> findAllWithFiltration(String text, List<Long> categories, Boolean isPaid, String start, String end, Boolean isAvailable, String sort, int from, int size);

    public EventFullDto getByEventId(long eventId);

    /**
     * Private: События
     **/
    public List<EventShortDto> getEventsByUser(long userId, int from, int size);

    public EventFullDto updateEventByUser(long userId, UpdateEventRequestDto dto);

    public EventFullDto addEventByUser(long userId, NewEventDto dto);

    public EventFullDto getEventByUser(long userId, long eventId);

    public EventFullDto cancelEventByUser(long userId, long eventId);

    public List<ParticipationRequestDto> getRequestsByEventId(long userId, long eventId);

    public ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId);

    public ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId);

    public List<EventFullDto> findAllByAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, int from, int size);

    public EventFullDto updateEventByAdmin(long userId, UpdateEventRequestDto dto);

    public EventFullDto publish(long eventId);

    public EventFullDto reject(long eventId);

}
