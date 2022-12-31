package ru.practicum.explore.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore.commons.dto.EventFullDto;
import ru.practicum.explore.commons.dto.NewEventDto;
import ru.practicum.explore.commons.dto.ParticipationRequestDto;
import ru.practicum.explore.commons.dto.UpdateEventRequestDto;
import ru.practicum.explore.repository.EventRepository;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;

    @Override
    public List<EventFullDto> getEventsByUser(long userId, int from, int size) {
        return eventRepository
                .findAllByUserId(userId, PageRequest.of((from / size), size))
                .stream()
                .map();
    }

    @Override
    public EventFullDto updateEventByUser(long userId, UpdateEventRequestDto dto) {
        return null;
    }

    @Override
    public EventFullDto addEventByUser(long userId, NewEventDto dto) {
        return null;
    }

    @Override
    public EventFullDto getEventByUser(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelEventByUser(long userId, long eventId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId) {
        return null;
    }

}
