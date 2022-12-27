package ru.practicum.explore.api.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CategoryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("categories")
public class GuestCategoryController {

    @GetMapping
    public List<CategoryDto> getCompilation(@RequestParam(required = false, defaultValue = "0") int from,
                                            @RequestParam(required = false, defaultValue = "0") int size) {
        return null;
    }

    @GetMapping(path = "/{catId}")
    public CategoryDto getEvent(@PathVariable long catId) {
        return null;
    }

}
