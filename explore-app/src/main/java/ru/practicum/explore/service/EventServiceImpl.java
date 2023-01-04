package ru.practicum.explore.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore.commons.dto.EventFullDto;
import ru.practicum.explore.commons.dto.NewEventDto;
import ru.practicum.explore.commons.dto.ParticipationRequestDto;
import ru.practicum.explore.commons.dto.UpdateEventRequestDto;
import ru.practicum.explore.commons.error.EntitiesNotConnectedException;
import ru.practicum.explore.commons.mapper.EventMapper;
import ru.practicum.explore.commons.model.Event;
import ru.practicum.explore.commons.model.User;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.StateRepository;
import ru.practicum.explore.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    UserRepository userRepository;
    StateRepository stateRepository;

    @Override
    public List<EventFullDto> getEventsByUser(long userId, int from, int size) {
        return eventRepository
                .findAllByUserId(userId, PageRequest.of((from / size), size))
                .stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByUser(long userId, UpdateEventRequestDto dto) {
        getUserOrThrow(userId);
        getOrThrowIfNotInitiator(dto.getEventId(), userId);
        Event e = eventRepository.save(EventMapper.toModel(dto));
        return EventMapper.toFullDto(e);
    }

    @Override
    public EventFullDto addEventByUser(long userId, NewEventDto dto) {
        getUserOrThrow(userId);
        Event e = EventMapper.toModel(dto);
        e.setState(stateRepository.getById(1L));
        e = eventRepository.save(e);
        return EventMapper.toFullDto(e);
    }

    @Override
    public EventFullDto getEventByUser(long userId, long eventId) {
        return EventMapper.toFullDto(
                getOrThrowIfNotInitiator(eventId, userId)
        );
    }

    @Override
    public EventFullDto cancelEventByUser(long userId, long eventId) {
        Event e = getOrThrowIfNotInitiator(eventId, userId);
        e.setState(stateRepository.getById(3L));
        e = eventRepository.save(e);
        return EventMapper.toFullDto(e);
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

    private User getUserOrThrow(long userId) throws EntityNotFoundException {
        try {
            return userRepository.getById(userId);
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(String.format("Пользователь ID=%d не существует", userId));
        }
    }

    private Event getEventOrThrow(long eventId) throws EntityNotFoundException {
        try {
            return eventRepository.getById(eventId);
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(String.format("Событие ID=%d не существует", eventId));
        }
    }

    private Event getOrThrowIfNotInitiator(long eventId, long userId) throws EntitiesNotConnectedException {
        Event e = eventRepository.getByIdAndUserId(eventId, userId);
        if (e == null) {
            throw new EntitiesNotConnectedException(
                String.format("Пользователь ID=%d не является инициатором события ID=%d", userId, eventId)
            );
        }
        return e;
    }

}
