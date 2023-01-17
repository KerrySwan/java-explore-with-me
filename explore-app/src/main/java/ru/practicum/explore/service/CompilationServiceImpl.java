package ru.practicum.explore.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.commons.dto.CompilationDto;
import ru.practicum.explore.commons.dto.NewCompilationDto;
import ru.practicum.explore.commons.mapper.CompilationMapper;
import ru.practicum.explore.commons.model.Compilation;
import ru.practicum.explore.commons.model.Event;
import ru.practicum.explore.repository.CompilationRepository;
import ru.practicum.explore.repository.EventRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public CompilationDto add(NewCompilationDto newCompilationsDto) {
        Compilation compilations = compilationRepository.save(CompilationMapper.toModel(newCompilationsDto));
        List<Event> events = eventRepository.findAllByEventIdIn(newCompilationsDto.getEvents());
        compilations.setEvents(events);
        return CompilationMapper.toDto(compilations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        return compilationRepository.findAllPinned(pinned, PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id")))
                .stream()
                .map(CompilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilation(Long compId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка  не найдена"));
        return CompilationMapper.toDto(compilations);
    }

    @Override
    public void delete(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEvent(Long compId, Long eventId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена"));
        List<Event> events = compilations.getEvents();
        events.removeIf(e -> e.getId() == eventId);
        compilations.setEvents(events);
        compilationRepository.save(compilations);
    }

    @Override
    public void addEvent(Long compId, Long eventId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена"));
        Event events = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие не найдено"));

        compilations.getEvents().add(events);
        compilationRepository.save(compilations);
    }

    @Override
    public void unpin(Long compId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена"));
        compilations.setPinned(false);
        compilationRepository.save(compilations);
    }

    @Override
    public void pin(Long compId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена"));
        compilations.setPinned(true);
        compilationRepository.save(compilations);
    }


}
