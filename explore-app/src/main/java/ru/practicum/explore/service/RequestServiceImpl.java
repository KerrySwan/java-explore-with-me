package ru.practicum.explore.service;

import org.springframework.stereotype.Service;
import ru.practicum.explore.commons.dto.ParticipationRequestDto;
import ru.practicum.explore.commons.mapper.RequestMapper;
import ru.practicum.explore.repository.RequestRepository;
import ru.practicum.explore.repository.StatusRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    RequestRepository requestRepository;
    StatusRepository statusRepository;

    @Override
    public List<ParticipationRequestDto> getRequestsByUserId(long userId) {
        return requestRepository.findAllByUserId(userId)
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addParticipationRequest(long userId, long eventId) {
        return ;
    }

    @Override
    public ParticipationRequestDto cancelRequestByUser(long userId, long requestId) {
        return null;
    }
}
