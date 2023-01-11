package ru.practicum.explore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    public List<Request> findAllByUserId(long userId);

    @Query(
            value =
                    "select r " +
                    "from Request r " +
                    "where r.event.id = :eventId" +
                    "  and r.event.user.id = :userId"
    )
    public List<Request> findAllByEventIdAndByEventUserId(long eventId, long userId);

    public Request getByIdAndUserId(long id, long userId);

    public Request getByIdAndEventId(long id,  long eventId);

}
