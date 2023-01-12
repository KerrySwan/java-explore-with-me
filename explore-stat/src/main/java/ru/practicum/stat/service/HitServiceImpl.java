package ru.practicum.stat.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stat.commons.dto.EndpointHitDto;
import ru.practicum.stat.commons.dto.ViewStatsDto;
import ru.practicum.stat.commons.mapper.EndpointHitMapper;
import ru.practicum.stat.commons.model.EndpointHit;
import ru.practicum.stat.repository.HitRepository;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    @Transactional
    public void addEndpointHit(EndpointHitDto endpointHitDto) {
        try {
            EndpointHit hit = EndpointHitMapper.toModel(endpointHitDto);
            hitRepository.save(hit);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ViewStatsDto> getView(String start, String end, List<String> uri, Boolean isDistinct) {
        LocalDateTime startDt = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDt = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (isDistinct) {
            return hitRepository.countTotalIpDistinct(startDt, endDt);
        } else {
            return hitRepository.countTotalIp(startDt, endDt);
        }
    }

}
