package ru.practicum.explore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.EventState;

@Repository
public interface StateRepository extends JpaRepository<EventState, Long> {
}
