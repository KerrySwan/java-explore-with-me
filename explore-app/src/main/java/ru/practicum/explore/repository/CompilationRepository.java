package ru.practicum.explore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Query(
            value =
                    "select c " +
                    "from Compilation c " +
                    "where (:pinned is null or c.pinned = :pinned)"
    )
    Page<Compilation> findAllPinned(Boolean pinned, Pageable p);

}