package ru.practicum.explore.service;

import ru.practicum.explore.commons.dto.CompilationDto;
import ru.practicum.explore.commons.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto add(NewCompilationDto newCompilationsDto);

    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilation(Long compId);

    void delete(Long compId);

    void deleteEvent(Long compId, Long eventId);

    void addEvent(Long compId, Long eventId);

    void unpin(Long compId);

    void pin(Long compId);

}
