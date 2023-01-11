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

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    public List<Event> findAllByUserId(long userId, Pageable p);

    public Event getByIdAndUserId(long id, long userId);

    @Query(
            value =
                    "Select e " +
                    "from Event e " +
                    "where (:categories is null or e.category.id in :categories)" +
                    "  and (:isPaid is null or e.paid = :isPaid)" +
                    "  and (cast(:rangeStart as date) is null or e.eventDate > :rangeStart) " +
                    "  and (cast(:rangeEnd as date) is null or e.eventDate < :rangeEnd)" +
                    "  and " +
                    "  (:isAvailable is null or " +
                        "(:isAvailable = true and e.participantLimit <> e.confirmedRequests) or " +
                        "(:isAvailable = false and e.participantLimit = e.confirmedRequests) " +
                       ")" +
                    "  and (:text is null or e.annotation like :text or e.description like :text)"
    )
    public Page<Event> findAllWithFiltration(String text, List<Long> categories, Boolean isPaid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean isAvailable, Pageable p);

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

}
