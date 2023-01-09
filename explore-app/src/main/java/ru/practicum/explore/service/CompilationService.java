package ru.practicum.explore.service;

import ru.practicum.explore.commons.dto.CompilationDto;
import ru.practicum.explore.commons.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto add(NewCompilationDto newCompilationsDto);

    List<CompilationDto> getAll(boolean pinned, int from, int size);

    CompilationDto getCompilation(long compId);

    void delete(long compId);

    void deleteEvent(long compId, long eventId);

    void addEvent(long compId, long eventId);

    void unpin(long compId);

    void pin(long compId);

}
