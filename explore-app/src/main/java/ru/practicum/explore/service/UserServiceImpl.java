package ru.practicum.explore.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.commons.dto.UserDto;
import ru.practicum.explore.commons.error.InvalidIdException;
import ru.practicum.explore.commons.mapper.UserMapper;
import ru.practicum.explore.commons.model.User;
import ru.practicum.explore.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Admin: Пользователи
     **/
    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        Page<User> users;
        if (ids != null) {
            for (Long id : ids) {
                if (id <= 0) throw new InvalidIdException("Переданное значение id меньше или равно нулю. ID = " + id);
            }
            users = userRepository.findAllById(ids, PageRequest.of((from / size), size));
        } else {
            users = userRepository.findAll(PageRequest.of((from / size), size));
        }
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(UserDto dto) {
        User u = userRepository.save(UserMapper.toModel(dto));
        return UserMapper.toDto(u);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

}
