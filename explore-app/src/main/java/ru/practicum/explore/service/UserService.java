package ru.practicum.explore.service;

import ru.practicum.explore.commons.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers(List<Long> ids, int from, int size);

    UserDto addUser(UserDto dto);

    void deleteUser(long id);


}
