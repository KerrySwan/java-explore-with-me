package ru.practicum.explore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query(
            value = "select e " +
                    "from Event e " +
                    "where e.id in :ids"
    )
    List<Event> findAllByEventIdIn(List<Long> ids);

    public List<Event> findAllByUserId(long userId, Pageable p);

    public Event getByIdAndUserId(long id, long userId);

    @Query(
            value =
                    "Select e " +
                            "from Event e " +
                            "where (:users is null or e.user.id in :users) " +
                            "  and (:states is null or e.state.name in (:states)) " +
                            "  and (:categories is null or e.category.id in :categories)" +
                            "  and (cast(:rangeStart as date) is null or e.eventDate > :rangeStart) " +
                            "  and (cast(:rangeEnd as date) is null or e.eventDate < :rangeEnd)"

    )
    public Page<Event> findAllByAdmin(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable p);

    public Optional<Event> getByIdAndStateId(long id, long stateId);
}
