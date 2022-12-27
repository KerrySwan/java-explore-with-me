package ru.practicum.explore.api.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/events")
public class GuestEventController {

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam String text,
                                        @RequestParam List<Long> categories,
                                        @RequestParam boolean paid,
                                        @RequestParam LocalDateTime rangeStart,
                                        @RequestParam LocalDateTime rangeEnd,
                                        @RequestParam(required = false, defaultValue = "false") boolean onlyAvailable,
                                        @RequestParam(required = false) String sort,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "0") int size) {
        return null;
    }

    @GetMapping(path = "/{id}")
    public EventFullDto getEvent(@PathVariable long id) {
        return null;
    }

}
