package ru.practicum.explore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.EventState;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<EventState, Long> {

    @Query(
            value = "select e " +
                    "from EventState e " +
                    "where e.name in :names"
    )
    public List<EventState> findAllByNames(List<String> names);

}
