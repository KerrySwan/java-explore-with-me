package ru.practicum.explore.api.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CompilationDto;
import ru.practicum.explore.service.CompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/compilations")
public class GuestCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilation(@RequestParam(required = false) Boolean pinned,
                                               @RequestParam(required = false, defaultValue = "0") int from,
                                               @RequestParam(required = false, defaultValue = "10") int size) {
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping(path = "/{compId}")
    public CompilationDto getEvent(@PathVariable long compId) {
        return compilationService.getCompilation(compId);
    }

}
