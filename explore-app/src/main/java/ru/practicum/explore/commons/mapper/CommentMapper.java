package ru.practicum.explore.commons.mapper;

import ru.practicum.explore.commons.dto.CommentDto;
import ru.practicum.explore.commons.dto.NewCommentDto;
import ru.practicum.explore.commons.model.Comment;

public class CommentMapper {

    public static Comment toModel(CommentDto dto){
        return Comment.builder()
                .id(dto.getId())
                .text(dto.getText())
                .event(EventMapper.toModel(dto.getEvent()))
                .user(UserMapper.toModel(dto.getUser()))
                .date(dto.getDate())
                .build();
    }

    public static Comment toModel(NewCommentDto dto){
        return Comment.builder()
                .text(dto.getText())
                .build();
    }

    public static CommentDto toDto(Comment model){
        return CommentDto.builder()
                .id(model.getId())
                .text(model.getText())
                .event(EventMapper.toShortDto(model.getEvent()))
                .user(UserMapper.toDto(model.getUser()))
                .date(model.getDate())
                .build();
    }
}
