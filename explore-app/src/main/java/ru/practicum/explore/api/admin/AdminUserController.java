package ru.practicum.explore.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.UserDto;
import ru.practicum.explore.commons.error.InvalidIdException;
import ru.practicum.explore.service.UserService;

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
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                                  @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(required = false, defaultValue = "0") @Positive int size) throws InvalidIdException{
        for (Long id : ids) {
            if (id<=0) throw new InvalidIdException("Переданное значние id меньше или равно нулю. ID = " + id);
        }
        return userService.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto dto) {
        return userService.addUser(dto);
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteMapping(@PathVariable @Positive long userId) {
        userService.deleteUser(userId);
        return "Пользователь удалён";
    }

}
