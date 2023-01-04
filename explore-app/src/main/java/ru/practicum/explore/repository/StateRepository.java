package ru.practicum.explore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.commons.model.EventState;

public interface StateRepository extends JpaRepository<EventState, Long> {
}
