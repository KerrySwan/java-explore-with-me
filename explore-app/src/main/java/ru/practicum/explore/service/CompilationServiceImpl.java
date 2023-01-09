package ru.practicum.explore.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public CompilationDto add(NewCompilationDto newCompilationsDto) {
        Compilation compilations = compilationRepository.saveAndFlush(CompilationMapper.toModel(newCompilationsDto));
        entityManager.refresh(compilations);
        return CompilationMapper.toDto(compilations);
    }

    @Override
    public List<CompilationDto> getAll(boolean pinned, int from, int size) {
        return compilationRepository.findAllPinned(pinned, PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id")))
                .stream()
                .map(CompilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CompilationDto getCompilation(long compId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка  не найдена"));
        return CompilationMapper.toDto(compilations);
    }

    @Override
    public void delete(long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEvent(long compId, long eventId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена"));
        List<Event> events = compilations.getEvents();
        events.remove(Event.builder().id(eventId).build());
        compilationRepository.save(compilations);
    }

    @Override
    public void addEvent(long compId, long eventId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена"));
        List<Event> events = compilations.getEvents();
        events.add(Event.builder().id(eventId).build());
        compilationRepository.save(compilations);
    }

    @Override
    public void unpin(long compId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена"));
        compilations.setPinned(false);
        compilationRepository.save(compilations);
    }

    @Override
    public void pin(long compId) {
        Compilation compilations = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена"));
        compilations.setPinned(true);
        compilationRepository.save(compilations);
    }


}
