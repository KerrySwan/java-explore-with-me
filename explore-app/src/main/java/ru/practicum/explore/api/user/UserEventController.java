package ru.practicum.explore.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.EventFullDto;
import ru.practicum.explore.commons.dto.NewEventDto;
import ru.practicum.explore.commons.dto.ParticipationRequestDto;
import ru.practicum.explore.commons.dto.UpdateEventRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user/{userId}/events")
public class UserEventController {

    @GetMapping
    public List<EventFullDto> getEvents(@PathVariable long userId,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "0") int size) {
        return null;
    }

    @PatchMapping
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @RequestBody UpdateEventRequestDto dto) {
        return null;
    }

    @PostMapping
    public List<EventFullDto> addEvent(@PathVariable long userId,
                                       @RequestBody NewEventDto dto) {
        return null;
    }

    @GetMapping(path = "/{eventId}")
    public EventFullDto getEventByEventId(@PathVariable long userId,
                                          @PathVariable long eventId) {
        return null;
    }

    @PatchMapping(path = "/{eventId}")
    public EventFullDto cancelEventByEventId(@PathVariable long userId,
                                             @PathVariable long eventId) {
        return null;
    }

    @GetMapping(path = "/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequestByEventId(@PathVariable long userId,
                                                                  @PathVariable long eventId) {
        return null;
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmEventRequestByReqId(@PathVariable long userId,
                                                              @PathVariable long eventId,
                                                              @PathVariable long reqId) {
        return null;
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectEventRequestByReqId(@PathVariable long userId,
                                                              @PathVariable long eventId,
                                                              @PathVariable long reqId) {
        return null;
    }

}
