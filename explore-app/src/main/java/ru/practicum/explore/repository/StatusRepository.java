package ru.practicum.explore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.RequestStatus;

@Repository
public interface StatusRepository extends JpaRepository<RequestStatus, Long> {
}
