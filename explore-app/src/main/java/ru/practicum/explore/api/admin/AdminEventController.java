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
    public List<EventFullDto> findEvent(@RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) LocalDateTime rangeStart,
                                        @RequestParam(required = false) LocalDateTime rangeEnd,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "10") int size) {
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
