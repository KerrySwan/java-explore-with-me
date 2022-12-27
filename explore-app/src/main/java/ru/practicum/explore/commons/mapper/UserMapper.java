package ru.practicum.explore.commons.mapper;

import ru.practicum.explore.commons.dto.UserDto;
import ru.practicum.explore.commons.dto.UserShortDto;
import ru.practicum.explore.commons.model.User;

public class UserMapper {

    static public UserDto toDto(User model){
        return UserDto.builder()
                .id(model.getId())
                .email(model.getEmail())
                .name(model.getName())
                .build();
    }

    static public UserShortDto toShortDto(User model){
        return UserShortDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    static public User toModel(UserDto dto){
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }

    static public User toModel(UserShortDto dto){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
