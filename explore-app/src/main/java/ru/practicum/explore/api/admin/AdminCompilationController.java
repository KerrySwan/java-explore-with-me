package ru.practicum.explore.api.admin;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CompilationDto;
import ru.practicum.explore.commons.dto.NewCompilationDto;
import ru.practicum.explore.service.CompilationService;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto addCategory(NewCompilationDto dto) {
        return compilationService.add(dto);
    }

    @DeleteMapping(path = "/{compId}")
    public String deleteCompilation(@PathVariable long compId) {
        compilationService.delete(compId);
        return "Подборка удалена";
    }

    @DeleteMapping(path = "/{compId}/events/{eventId}")
    public String deleteEventFromCompilation(@PathVariable long compId,
                                             @PathVariable long eventId) {
        compilationService.deleteEvent(compId, eventId);
        return "Событие удалено из подборки";
    }

    @PatchMapping(path = "/{compId}/events/{eventId}")
    public String addEventToCompilation(@PathVariable long compId,
                                        @PathVariable long eventId) {
        compilationService.addEvent(compId, eventId);
        return "Событие добавлено";
    }

    @DeleteMapping(path = "/{compId}/pin")
    public String unpinCompilation(@PathVariable long compId) {
        compilationService.unpin(compId);
        return "Подборка откреплена";
    }

    @PatchMapping(path = "/{compId}/pin")
    public String pinCompilation(@PathVariable long compId) {
        compilationService.pin(compId);
        return "Подборка закреплена";
    }

}
