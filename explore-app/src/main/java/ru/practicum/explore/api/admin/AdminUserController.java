package ru.practicum.explore.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.UserDto;
import ru.practicum.explore.commons.dto.UserShortDto;
import ru.practicum.explore.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                               @RequestParam(required = false, defaultValue = "0") int from,
                               @RequestParam(required = false, defaultValue = "0") int size) {
        return userService.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto dto){
        return userService.addUser(dto);
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteMapping(@PathVariable long userId){
        userService.deleteUser(userId);
        return "Пользователь удалён";
    }

}
