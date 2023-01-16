package ru.practicum.explore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.commons.model.Comment;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByEventId(long eventId, Pageable p);

    Optional<Comment> getByIdAndEventId(long id, long eventId);

    Optional<Comment> getByIdAndEventIdAndUserId(long id, long eventId, long userId);

}
