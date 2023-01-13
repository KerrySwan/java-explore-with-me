package ru.practicum.explore.service;

import ru.practicum.explore.commons.dto.EventFullDto;
import ru.practicum.explore.commons.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface HitService {

    void addHit(HttpServletRequest req) throws URISyntaxException, IOException, InterruptedException;

    EventFullDto getView(HttpServletRequest req, EventFullDto dto) throws URISyntaxException, IOException, InterruptedException;

    List<EventShortDto> getView(HttpServletRequest req, List<EventShortDto> dtos) throws URISyntaxException, IOException, InterruptedException;


}
