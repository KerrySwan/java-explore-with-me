package ru.practicum.explore.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.commons.dto.ParticipationRequestDto;
import ru.practicum.explore.commons.mapper.RequestMapper;
import ru.practicum.explore.commons.model.Event;
import ru.practicum.explore.commons.model.Request;
import ru.practicum.explore.commons.model.RequestStatus;
import ru.practicum.explore.commons.model.User;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.RequestRepository;
import ru.practicum.explore.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    /**
     * Private: Запросы на участие
     **/
    @Override
    public List<ParticipationRequestDto> getRequestsByUserId(long userId) {
        return requestRepository.findAllByUserId(userId)
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ParticipationRequestDto addParticipationRequest(long userId, long eventId) {
        User u;
        Event e;
        try {
            u = userRepository.getById(userId);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(
                    String.format("userId = %d не найден", userId)
            );
        }
        try {
            e = eventRepository.getByIdAndUserId(eventId, userId);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(
                    String.format("Связка eventId = %d и userId = %d не найдена", eventId, userId)
            );
        }
        Request r = Request.builder()
                .user(u)
                .event(e)
                .createdOn(LocalDateTime.now())
                .status(new RequestStatus(1L, "PENDING"))
                .build();
        return RequestMapper.toDto(requestRepository.save(r));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ParticipationRequestDto cancelRequestByUser(long userId, long requestId) {
        Request r;
        try {
            r = requestRepository.getByIdAndUserId(requestId, userId);
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException(
                    String.format("Связка requestId = %d и userId = %d не найдена", requestId, userId)
            );
        }
        if (r.getStatus().getId() == 2L) {
            Event e = eventRepository.getById(r.getEvent().getId());
            e.setConfirmedRequests(e.getConfirmedRequests() - 1L);
            eventRepository.save(e);
        }
        r.setStatus(new RequestStatus(4L, "CANCELED"));
        return RequestMapper.toDto(requestRepository.save(r));
    }
}
