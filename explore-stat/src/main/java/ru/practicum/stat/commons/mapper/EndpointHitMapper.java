package ru.practicum.stat.commons.mapper;

import ru.practicum.stat.commons.dto.EndpointHitDto;
import ru.practicum.stat.commons.model.EndpointHit;

import java.net.URISyntaxException;

public class EndpointHitMapper {

    public static EndpointHit toModel(EndpointHitDto statsHitDto) throws URISyntaxException {
        return EndpointHit.builder()
                .app(statsHitDto.getApp())
                .uri(statsHitDto.getUri())
                .timestamp(statsHitDto.getTimestamp())
                .ip(statsHitDto.getIp())
                .build();
    }

}
