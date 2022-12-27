package ru.practicum.explore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            nativeQuery = true,
            value =
                    "SELECT " +
                            "* " +
                    "FROM user " +
                    "WHERE id in (:ids)"
    )
    List<User> findAllById(Iterable<Long> ids, Pageable p);

}
