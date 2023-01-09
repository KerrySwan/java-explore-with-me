package ru.practicum.explore.commons.mapper;

import ru.practicum.explore.commons.dto.CompilationDto;
import ru.practicum.explore.commons.dto.NewCompilationDto;
import ru.practicum.explore.commons.model.Compilation;
import ru.practicum.explore.commons.model.Event;

import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation toModel(NewCompilationDto dto) {
        return Compilation.builder()
                .events(dto.getEvents()
                        .stream()
                        .map(el -> Event.builder().id(el).build())
                        .collect(Collectors.toList()))
                .pinned(dto.isPinned())
                .title(dto.getTitle())
                .build();
    }

    public static CompilationDto toDto(Compilation compilations) {
        return CompilationDto.builder()
                .id(compilations.getId())
                .events(compilations.getEvents().stream()
                        .map(Event::getId)
                        .collect(Collectors.toList()))
                .pinned(compilations.isPinned())
                .title(compilations.getTitle())
                .build();
    }

}
