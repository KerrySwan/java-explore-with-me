package ru.practicum.explore.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore.commons.dto.*;
import ru.practicum.explore.commons.mapper.EventMapper;
import ru.practicum.explore.commons.mapper.RequestMapper;
import ru.practicum.explore.commons.model.Event;
import ru.practicum.explore.commons.model.Request;
import ru.practicum.explore.commons.model.User;
import ru.practicum.explore.repository.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    UserRepository userRepository;
    RequestRepository requestRepository;
    StateRepository stateRepository;
    StatusRepository statusRepository;

    //Private
    @Override
    public List<EventShortDto> getEventsByUser(long userId, int from, int size) {
        return eventRepository
                .findAllByUserId(userId, PageRequest.of((from / size), size))
                .stream()
                .map(EventMapper::toShortDto)
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
        getUserOrThrow(userId);
        return EventMapper.toFullDto(
                getOrThrowIfNotInitiator(eventId, userId)
        );
    }

    @Override
    public EventFullDto cancelEventByUser(long userId, long eventId) {
        getUserOrThrow(userId);
        Event e = getOrThrowIfNotInitiator(eventId, userId);
        e.setState(stateRepository.getById(3L));
        e = eventRepository.save(e);
        return EventMapper.toFullDto(e);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(long userId, long eventId) {
        return requestRepository
                .findAllByUserIdAndEventId(userId, eventId)
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId) {
        getUserOrThrow(userId);
        getEventOrThrow(eventId);
        //нельзя если отклонено
        Request r = requestRepository.getByIdAndUserIdAndEventId(reqId, userId, eventId);
        if (r.getStatus().equals(statusRepository.getById(3L))) throw ConditionsNotFulfiledException;
        r.setStatus(statusRepository.getById(2L));
        return RequestMapper.toDto(r);
    }

    @Override
    public ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId) {
        getUserOrThrow(userId);
        getEventOrThrow(eventId);
        Request r = requestRepository.getByIdAndUserIdAndEventId(reqId, userId, eventId);
        r.setStatus(statusRepository.getById(3L));
        return RequestMapper.toDto(r);
    }

    //Объект не найден 404
    private User getUserOrThrow(long userId) throws EntityNotFoundException {
        try {
            return userRepository.getById(userId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(String.format("Пользователь ID=%d не существует", userId));
        }
    }

    //Объект не найден 404
    private Event getEventOrThrow(long eventId) throws EntityNotFoundException {
        try {
            return eventRepository.getById(eventId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(String.format("Событие ID=%d не существует", eventId));
        }
    }

    //Объект не найден 404
    private Event getOrThrowIfNotInitiator(long eventId, long userId) throws EntityNotFoundException {
        Event e = eventRepository.getByIdAndUserId(eventId, userId);
        if (e == null) {
            throw new EntityNotFoundException(
                    String.format("Пользователь ID=%d не является инициатором события ID=%d", userId, eventId)
            );
        }
        return e;
    }

}
