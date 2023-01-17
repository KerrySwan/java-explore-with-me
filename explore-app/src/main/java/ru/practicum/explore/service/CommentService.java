package ru.practicum.explore.service;

import ru.practicum.explore.commons.dto.CommentDto;
import ru.practicum.explore.commons.dto.NewCommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getComments(long eventId,
                                 int from,
                                 int size);

    CommentDto getComment(long eventId,
                          long commentId);

    CommentDto addComment(long userId,
                          long eventId,
                          NewCommentDto commentDto);

    void deleteComment(long userId,
                       long eventId,
                       long commentId);

    CommentDto updateComment(long userId,
                             long eventId,
                             long commentId,
                             NewCommentDto comment);


    void deleteCommentByAdmin(long commentId);


    CommentDto updateCommentByAdmin(long commentId,
                                    NewCommentDto commentDto);

}
