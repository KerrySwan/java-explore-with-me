package ru.practicum.explore.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.*;
import ru.practicum.explore.service.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user/{userId}/events")
public class UserEventController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable long userId,
                                         @RequestParam(required = false, defaultValue = "0") int from,
                                         @RequestParam(required = false, defaultValue = "0") int size) {
        return eventService.getEventsByUser(userId, from, size);
    }

    @PatchMapping
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @RequestBody UpdateEventRequestDto dto) {
        return eventService.updateEventByUser(userId, dto);
    }

    @PostMapping
    public EventFullDto addEvent(@PathVariable long userId,
                                 @RequestBody NewEventDto dto) {
        return eventService.addEventByUser(userId, dto);
    }

    @GetMapping(path = "/{eventId}")
    public EventFullDto getEventByEventId(@PathVariable long userId,
                                          @PathVariable long eventId) {
        return eventService.getEventByUser(userId, eventId);
    }

    @PatchMapping(path = "/{eventId}")
    public EventFullDto cancelEventByEventId(@PathVariable long userId,
                                             @PathVariable long eventId) {
        return eventService.cancelEventByUser(userId, eventId);
    }

    @GetMapping(path = "/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequestByEventId(@PathVariable long userId,
                                                                  @PathVariable long eventId) {
        return eventService.getRequestsByUser(userId, eventId);
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmEventRequestByReqId(@PathVariable long userId,
                                                              @PathVariable long eventId,
                                                              @PathVariable long reqId) {
        return eventService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectEventRequestByReqId(@PathVariable long userId,
                                                             @PathVariable long eventId,
                                                             @PathVariable long reqId) {
        return eventService.rejectRequest(userId, eventId, reqId);
    }

}
