package ru.practicum.explore.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explore.commons.dto.CommentDto;
import ru.practicum.explore.commons.dto.NewCommentDto;
import ru.practicum.explore.commons.mapper.CommentMapper;
import ru.practicum.explore.commons.model.Comment;
import ru.practicum.explore.repository.CommentRepository;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    CommentRepository commentRepository;
    EventRepository eventRepository;
    UserRepository userRepository;

    @Override
    public List<CommentDto> getComments(long eventId,
                                        int from,
                                        int size){
        try {
            eventRepository.getById(eventId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    String.format("События eventId = %d не cуществует", eventId)
            );
        }
        Page<Comment> comments = commentRepository.findAllByEventId(eventId, PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "date")));
        return comments.stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getComment(long commentId,
                          long eventId){
        Comment comment = commentRepository.getByIdAndEventId(commentId, eventId)
                .orElseThrow( () ->
                    new EntityNotFoundException(
                            String.format("Связка commentId = %d и eventId = %d не найдена", commentId, eventId)
                    )
                );
        return CommentMapper.toDto(comment);
    }

    @Override
    public CommentDto addComment(long userId,
                          long eventId,
                          NewCommentDto commentDto){
        Comment comment = CommentMapper.toModel(commentDto);
        comment.setDate(LocalDateTime.now());
        try {
            comment.setEvent(
                    eventRepository.getById(eventId)
            );
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    String.format("События eventId = %d не cуществует", eventId)
            );
        }
        try {
            comment.setUser(
                    userRepository.getById(userId)
            );
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    String.format("Пользователя userId = %d не cуществует", userId)
            );
        }
        return CommentMapper.toDto(
                commentRepository.save(comment)
        );
    }

    @Override
    public void deleteComment(long commentId,
                       long userId,
                       long eventId){
        commentRepository.getByIdAndEventIdAndUserId(commentId, eventId, userId)
                .orElseThrow(()->
                    new EntityNotFoundException(
                            String.format("Связки commentId = %d, eventId = %d и userId = %d не существует", commentId, eventId, userId)
                    )
                );
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto updateComment(long userId,
                            long eventId,
                            long commentId,
                            NewCommentDto commentDto){
        Comment comment = commentRepository.getByIdAndEventIdAndUserId(commentId, eventId, userId)
                .orElseThrow( ()->
                    new EntityNotFoundException(
                            String.format("Связки commentId = %d, eventId = %d и userId = %d не существует", commentId, eventId, userId)
                    )
                );
        comment.setText(commentDto.getText());
        return CommentMapper.toDto(
                commentRepository.save(comment)
        );
    }

    @Override
    public void deleteCommentByAdmin(long commentId){
        try {
            Comment comment = commentRepository.getById(commentId);
            commentRepository.delete(comment);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    String.format("Комментария commentId = %d не существует", commentId)
            );
        }
    }

    @Override
    public CommentDto updateCommentByAdmin(long commentId,
                                    NewCommentDto commentDto){
        try {
            Comment comment = commentRepository.getById(commentId);
            comment.setText(commentDto.getText());
            return CommentMapper.toDto(
                    commentRepository.save(comment)
            );
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    String.format("Комментария commentId = %d, не существует", commentId)
            );
        }
    }

}
