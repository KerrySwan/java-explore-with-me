package ru.practicum.explore.api.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.EventFullDto;
import ru.practicum.explore.commons.dto.EventShortDto;
import ru.practicum.explore.service.EventService;
import ru.practicum.explore.service.HitService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/events")
public class GuestEventController {

    private final EventService eventService;
    private final HitService hitService;

    @GetMapping
    public List<EventShortDto> getEvents(HttpServletRequest request,
                                         @RequestParam(required = false) String text,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false) Boolean paid,
                                         @RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(required = false) String sort,
                                         @RequestParam(required = false, defaultValue = "0") int from,
                                         @RequestParam(required = false, defaultValue = "10") int size) throws URISyntaxException, IOException, InterruptedException {
        List<EventShortDto> dtoList = eventService.findAllWithFiltration(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return hitService.getView(request, dtoList);
    }

    @GetMapping(path = "/{id}")
    public EventFullDto getEvent(HttpServletRequest request,
                                 @PathVariable long id) throws URISyntaxException, IOException, InterruptedException {
        EventFullDto dto = eventService.getByEventId(id);
        return hitService.getView(request, dto);
    }

}
