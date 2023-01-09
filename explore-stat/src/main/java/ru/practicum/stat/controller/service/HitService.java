package ru.practicum.stat.controller.service;

import ru.practicum.stat.commons.dto.EndpointHitDto;
import ru.practicum.stat.commons.dto.ViewStatsDto;

import java.util.List;

public interface HitService {

    void addEndpointHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getView(String start, String end, List<String> uri, Boolean isDistinct);

}
