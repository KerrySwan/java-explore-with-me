package ru.practicum.explore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.Event;
import ru.practicum.explore.commons.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public List<Event> findAllByUserId(long userId, Pageable p);

    public Event getByIdAndUserId(long id, long userId);

    @Query(
            nativeQuery = true,
            value =
                    "Select * " +
                            "from event " +
                            "where category_id in :categories" +
                            "  and paid = :isPaid" +
                            "  and event_date > :rangeStart " +
                            "  and event_date < :rangeEnd" +
                            "  and " +
                            "  ( (:isAvailable and participantLimit <> confirmed_requests) or (not :isAvailable and participantLimit = confirmed_requests) )" +
                            "  and " +
                            "  (annotation ilike :text or description ilike :text )"

    )
    public Page<Event> findAllWithFiltration(String text, List<Long> categories, boolean isPaid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean isAvailable, Pageable pageable);

    @Query(
            nativeQuery = true,
            value =
                    "Select * " +
                    "from event " +
                    "where user_id in :users " +
                    "  and state in :states " +
                    "  and category_id in :categories" +
                    "  and event_date > :rangeStart " +
                    "  and event_date < :rangeEnd"

    )
    public Page<Event> findAllByAdmin(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable p);

}
