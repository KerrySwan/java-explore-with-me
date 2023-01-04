package ru.practicum.explore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.Event;
import ru.practicum.explore.commons.model.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public List<Event> findAllByUserId(long userId, Pageable p);

    public Event getByIdAndUserId(long id, long userId);

}
