package ru.practicum.explore.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.commons.dto.*;
import ru.practicum.explore.commons.error.ConditionsNotFulfilledException;
import ru.practicum.explore.commons.error.InvalidIdException;
import ru.practicum.explore.commons.mapper.EventMapper;
import ru.practicum.explore.commons.mapper.RequestMapper;
import ru.practicum.explore.commons.model.Event;
import ru.practicum.explore.commons.model.EventState;
import ru.practicum.explore.commons.model.Request;
import ru.practicum.explore.repository.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final StateRepository stateRepository;
    private final StatusRepository statusRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Public: События
     **/

    @Override
    public List<EventShortDto> findAllWithFiltration(String text, List<Long> categories, boolean isPaid, LocalDateTime start, LocalDateTime end, boolean isAvailable, String sort, int from, int size) {
        Sort sorting;
        switch (sort) {
            case "EVENT_DATE":
                sorting = Sort.by(Sort.Direction.ASC, "eventDate");
                break;
            case "VIEWS":
                sorting = Sort.by(Sort.Direction.ASC, "views");
                break;
            default:
                sorting = Sort.by(Sort.Direction.ASC, "id");
        }
        Page<Event> e = eventRepository.findAllWithFiltration(text, categories, isPaid, start, end, isAvailable, PageRequest.of(from / size, size, sorting));
        return e.stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getByEventId(long eventId) {
        try {
            return EventMapper.toFullDto(eventRepository.getById(eventId));
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(
                    String.format("eventId = %d не найдено", eventId)
            );
        }
    }

    /**
     * Private: События
     **/
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
        try {
            eventRepository.getByIdAndUserId(dto.getEventId(), userId);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(
                    String.format("Связка eventId = %d и userId = %d не найдена", dto.getEventId(), userId)
            );
        }
        Event e = eventRepository.save(EventMapper.toModel(dto));
        return EventMapper.toFullDto(e);
    }

    @Override
    public EventFullDto addEventByUser(long userId, NewEventDto dto) {
        try {
            userRepository.getById(userId);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(
                    String.format("userId = %d не найден", userId)
            );
        }
        Event e = EventMapper.toModel(dto);
        e.setState(stateRepository.getById(1L));
        e = eventRepository.save(e);
        return EventMapper.toFullDto(e);
    }

    @Override
    public EventFullDto getEventByUser(long userId, long eventId) {
        try {
            return EventMapper.toFullDto(
                    eventRepository.getByIdAndUserId(eventId, userId)
            );
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Связка eventId = %d и userId = %d не найдена", eventId, userId));
        }
    }

    @Override
    public EventFullDto cancelEventByUser(long userId, long eventId) {
        Event e;
        try {
            e = eventRepository.getByIdAndUserId(eventId, userId);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Связка eventId = %d и userId = %d не найдена", eventId, userId));
        }
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
        Request r;
        try {
            r = requestRepository.getByIdAndUserIdAndEventId(reqId, userId, eventId);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Связка eventId = %d, userId = %d и requestId = %d не найдена", eventId, userId, reqId));
        }
        if (r.getStatus().getId() == 3L)
            throw new ConditionsNotFulfilledException("Запрос на участие был отменен ранее");
        r.setStatus(statusRepository.getById(2L));
        Event e = eventRepository.getById(eventId);
        if (e.getConfirmedRequests() < e.getParticipantLimit()) {
            e.setConfirmedRequests(e.getConfirmedRequests() + 1L);
            eventRepository.save(e);
        }
        return RequestMapper.toDto(requestRepository.save(r));
    }

    @Override
    public ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId) {
        Request r;
        try {
            r = requestRepository
                    .getByIdAndUserIdAndEventId(reqId, userId, eventId);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Связка eventId = %d, userId = %d и requestId = %d не найдена", eventId, userId, reqId));
        }
        if (r.getStatus().getId() == 2L) {
            Event e = eventRepository.getById(eventId);
            e.setConfirmedRequests(e.getConfirmedRequests() - 1L);
            eventRepository.save(e);
        }
        r.setStatus(statusRepository.getById(3L));
        return RequestMapper.toDto(requestRepository.save(r));
    }

    /**
     * Admin: События
     **/

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<EventFullDto> findAllByAdmin(List<Long> users,
                                             List<String> states,
                                             List<Long> categories,
                                             LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd,
                                             int from,
                                             int size) {
        Page<Event> events = eventRepository.findAllByAdmin(
                users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"))
        );
        return events.stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByAdmin(long userId, UpdateEventRequestDto dto) {
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("eventId = %d не найдено", dto.getEventId())));

        if (event.getUser().getId() != userId) {
            throw new InvalidIdException("Редактировать может только владелец события ");
        }
        if (event.getState().equals("PUBLISHED")) {
            throw new InvalidIdException("Изменить можно только отмененные события или события в состоянии ожидания модерации");
        }
        if (event.getState().equals("CANCELED")) {
            event.setState(new EventState(1, "PENDING"));
        }

        event = updateDto(event, dto);
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    Event updateDto(Event event, UpdateEventRequestDto dto) {
        if (dto.getAnnotation() != null && !dto.getAnnotation().isEmpty()) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategoryId() != null) {
            event.setCategory(categoryRepository.getById(dto.getCategoryId()));
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            event.setTitle(dto.getTitle());
        }
        return event;
    }

    @Override
    public EventFullDto publish(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidIdException("Событие не найдено"));
        event.setState(new EventState(2, "PUBLISHED"));
        event.setPublishedOn(LocalDateTime.now());
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto reject(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidIdException("Событие не найдено"));
        event.setState(new EventState(3, "CANCELED"));
        return EventMapper.toFullDto(eventRepository.save(event));
    }

}
