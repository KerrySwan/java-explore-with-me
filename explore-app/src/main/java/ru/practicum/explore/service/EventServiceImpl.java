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
import ru.practicum.explore.config.JacksonConfiguration;
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
    public List<EventShortDto> findAllWithFiltration(String text, List<Long> categories, Boolean isPaid, String rangeStart, String rangeEnd, Boolean isAvailable, String sort, int from, int size) {
        Sort sorting;
        if (sort == null)
            sorting = Sort.by(Sort.Direction.ASC, "id");
        else if (sort.equals("EVENT_DATE"))
            sorting = Sort.by(Sort.Direction.ASC, "eventDate");
        else if (sort.equals("VIEWS"))
            sorting = Sort.by(Sort.Direction.ASC, "views");
        else
            sorting = Sort.by(Sort.Direction.ASC, "id");
        LocalDateTime start = rangeStart == null ? null : LocalDateTime.parse(rangeStart, JacksonConfiguration.dtf);
        LocalDateTime end = rangeEnd == null ? null : LocalDateTime.parse(rangeEnd, JacksonConfiguration.dtf);
        Page<Event> events = eventRepository.findAll((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("state"), new EventState(2, "PUBLISHED")),
                                root.get("category").in(categories),
                                criteriaBuilder.equal(root.get("paid"), isPaid),
                                (start != null && end != null) ?
                                        criteriaBuilder.and(
                                                criteriaBuilder.greaterThan(root.get("eventDate"), start),
                                                criteriaBuilder.lessThan(root.get("eventDate"), end)
                                        ) : criteriaBuilder.lessThan(root.get("eventDate"), LocalDateTime.now()),
                                (isAvailable) ? criteriaBuilder.or(
                                        criteriaBuilder.equal(root.get("participantLimit"), 0),
                                        criteriaBuilder.and(
                                                criteriaBuilder.notEqual(root.get("participantLimit"), 0),
                                                criteriaBuilder.greaterThan(root.get("participantLimit"), root.get("confirmedRequests"))
                                        )) : root.isNotNull(),
                                criteriaBuilder.or(
                                        criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")), "%" + text.toLowerCase() + "%"),
                                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + text.toLowerCase() + "%")
                                )),
                PageRequest.of(from / size, size, sorting));
        return events.stream()
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
        Event e = eventRepository.getByIdAndUserId(dto.getEventId(), userId);
        e = updateEvent(e, dto);
        e = eventRepository.save(e);
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
        e.setCategory(categoryRepository.getById(dto.getCategory()));
        e.setUser(userRepository.getById(userId));
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
    public List<ParticipationRequestDto> getRequestsByEventId(long userId, long eventId) {
        return requestRepository
                .findAllByEventIdAndByEventUserId(eventId, userId)
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId) {
        Request r;
        try {
            eventRepository.getByIdAndUserId(eventId, userId);
        }  catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Связка eventId = %d и userId = %d не найдена", eventId, userId));
        }
        try {
            r = requestRepository.getByIdAndEventId(reqId, eventId);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Связка eventId = %d и requestId = %d не найдена", eventId, reqId));
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
            eventRepository.getByIdAndUserId(eventId, userId);
        }  catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(String.format("Связка eventId = %d и userId = %d не найдена", eventId, userId));
        }
        try {
            r = requestRepository
                    .getByIdAndEventId(reqId, eventId);
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
                                             String rangeStart,
                                             String rangeEnd,
                                             int from,
                                             int size) {
        LocalDateTime start = rangeStart == null ? null : LocalDateTime.parse(rangeStart, JacksonConfiguration.dtf);
        LocalDateTime end = rangeEnd == null ? null : LocalDateTime.parse(rangeEnd, JacksonConfiguration.dtf);
        List<EventState> stateList = stateRepository.findAllByNames(states);
        Page<Event> events = eventRepository.findAll((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                (users != null) ? root.get("user").in(users) : root.isNotNull(),
                                (states != null) ? root.get("state").in(stateList) : root.isNotNull(),
                                (categories != null) ? root.get("category").in(categories) : root.isNotNull(),
                                (start != null && end != null) ?
                                        criteriaBuilder.and(
                                                criteriaBuilder.greaterThan(root.get("eventDate"), start),
                                                criteriaBuilder.lessThan(root.get("eventDate"), end)
                                        ) : criteriaBuilder.lessThan(root.get("eventDate"), LocalDateTime.now())
                        ),
                PageRequest.of(from/size, size, Sort.by(Sort.Direction.ASC, "id")));
        return events.stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventByAdmin(long eventId, UpdateEventRequestDto dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("eventId = %d не найдено", dto.getEventId())));
        event = updateEventWithDto(event, dto);
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    Event updateEventWithDto(Event event, UpdateEventRequestDto dto) {
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

    private Event updateEvent(Event e, UpdateEventRequestDto dto) {
        if (dto.getAnnotation() != null) e.setAnnotation(dto.getAnnotation());
        if (dto.getCategoryId() != null) e.setCategory(categoryRepository.getById(dto.getCategoryId()));
        if (dto.getDescription() != null) e.setDescription(dto.getDescription());
        if (dto.getEventDate() != null) e.setEventDate(dto.getEventDate());
        if (dto.getPaid() != null) e.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) e.setParticipantLimit(dto.getParticipantLimit());
        if (dto.getTitle() != null) e.setTitle(dto.getTitle());
        return e;
    }
}
