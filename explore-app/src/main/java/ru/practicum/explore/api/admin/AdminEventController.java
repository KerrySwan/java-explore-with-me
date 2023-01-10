package ru.practicum.explore.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.EventFullDto;
import ru.practicum.explore.commons.dto.UpdateEventRequestDto;
import ru.practicum.explore.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    @GetMapping()
    public List<EventFullDto> findEvent(@RequestParam List<Long> users,
                                        @RequestParam List<String> states,
                                        @RequestParam List<Long> categories,
                                        @RequestParam LocalDateTime rangeStart,
                                        @RequestParam LocalDateTime rangeEnd,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "0") int size) {
        return eventService.findAllByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping(path = "/{eventId}")
    public EventFullDto updateEvent(@PathVariable long eventId, @RequestBody UpdateEventRequestDto dto) {
        return eventService.updateEventByAdmin(eventId, dto);
    }

    @PatchMapping(path = "/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable long eventId) {
        return eventService.publish(eventId);
    }

    @PatchMapping(path = "/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable long eventId) {
        return eventService.reject(eventId);
    }

}
