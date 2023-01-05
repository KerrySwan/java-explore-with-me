package ru.practicum.explore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    //Admin
    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size){
        List<User> u = userRepository.findAllById(ids, PageRequest.of((from / size), size));
        return u.stream()
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
