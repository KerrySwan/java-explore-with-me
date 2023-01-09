package ru.practicum.explore.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.ParticipationRequestDto;
import ru.practicum.explore.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user/{userId}/requests")
public class UserRequestController {

    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId){
        return requestService.getRequestsByUserId(userId);
    }

    @PostMapping
    public ParticipationRequestDto sendRequest(@PathVariable long userId,
                                               @RequestParam long eventId){
        return requestService.addParticipationRequest(userId, eventId);
    }

    @PatchMapping(path = "/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId,
                                                 @PathVariable long requestId) {
        return requestService.cancelRequestByUser(userId, requestId);
    }

}
