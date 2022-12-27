package ru.practicum.explore.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.AdminUpdateEventRequestDto;
import ru.practicum.explore.commons.dto.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/events")
public class AdminEventController {

    @GetMapping()
    public List<EventFullDto> findEvent(@RequestParam List<Long> users,
                                        @RequestParam List<String> states,
                                        @RequestParam List<Long> categories,
                                        @RequestParam LocalDateTime rangeStart,
                                        @RequestParam LocalDateTime rangeEnd,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "0") int size) {
        return null;
    }

    @PutMapping(path = "/{eventId}")
    public AdminUpdateEventRequestDto updateEvent(@PathVariable long eventId){
        return null;
    }

    @PatchMapping(path = "/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable long eventId){
        return null;
    }

    @PatchMapping(path = "/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable long eventId){
        return null;
    }

}
