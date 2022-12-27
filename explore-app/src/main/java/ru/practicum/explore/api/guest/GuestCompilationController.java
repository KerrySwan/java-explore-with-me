package ru.practicum.explore.api.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CompilationDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/compilations")
public class GuestCompilationController {

    @GetMapping
    public List<CompilationDto> getCompilation(@RequestParam boolean pinned,
                                               @RequestParam(required = false, defaultValue = "0") int from,
                                               @RequestParam(required = false, defaultValue = "0") int size) {
        return null;
    }

    @GetMapping(path = "/{compId}")
    public CompilationDto getEvent(@PathVariable long compId) {
        return null;
    }

}
