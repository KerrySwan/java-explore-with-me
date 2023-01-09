package ru.practicum.explore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    public List<Request> findAllByUserId(long userId);

    public List<Request> findAllByUserIdAndEventId(long userId, long eventId);

    public Request getByIdAndUserId(long id, long userId);

    public Request getByIdAndUserIdAndEventId(long id, long userId, long eventId);

}
