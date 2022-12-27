package ru.practicum.explore.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user/{userId}/requests")
public class UserRequestController {

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId){
        return null;
    }

    @PostMapping
    public ParticipationRequestDto sendRequest(@PathVariable long userId,
                                               @RequestParam long eventId){
        return null;
    }

    @PatchMapping(path = "/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId,
                                                 @PathVariable long requestId) {
        return null;
    }

}
