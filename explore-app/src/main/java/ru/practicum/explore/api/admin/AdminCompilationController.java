package ru.practicum.explore.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CompilationDto;
import ru.practicum.explore.commons.dto.NewCompilationDto;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class AdminCompilationController {

    @PostMapping
    public CompilationDto addCategory(NewCompilationDto dto){
        return null;
    }

    @DeleteMapping(path = "/{compId}")
    public String deleteCompilation(@PathVariable long compId){
        return "Подборка удалена";
    }

    @DeleteMapping(path = "/{compId}/events/{eventId}")
    public String deleteEventFromCompilation(@PathVariable long compId,
                                             @PathVariable long eventId){
        return "Событие удалено из подборки";
    }

    @PatchMapping(path = "/{compId}/events/{eventId}")
    public String addEventToCompilation(@PathVariable long compId,
                                        @PathVariable long eventId){
        return "Событие добавлено";
    }

    @DeleteMapping(path = "/{compId}/pin")
    public String unpinCompilation(@PathVariable long compId){
        return "Подборка откреплена";
    }

    @PatchMapping(path = "/{compId}/pin")
    public String pinCompilation(@PathVariable long compId){
        return "Подборка закреплена";
    }

}
