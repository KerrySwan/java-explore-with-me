package ru.practicum.explore.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.UserDto;
import ru.practicum.explore.commons.error.InvalidIdException;
import ru.practicum.explore.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(required = false, defaultValue = "0") int from,
                                  @RequestParam(required = false, defaultValue = "10") int size) throws InvalidIdException {
        return userService.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto dto) {
        return userService.addUser(dto);
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteMapping(@PathVariable @Positive long userId) {
        userService.deleteUser(userId);
        return "Пользователь удалён";
    }

}
