package ru.practicum.explore.api.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CategoryDto;
import ru.practicum.explore.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("categories")
public class GuestCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCompilation(@RequestParam(required = false, defaultValue = "0") int from,
                                            @RequestParam(required = false, defaultValue = "10") int size) {
        return categoryService.getAll(from, size);
    }

    @GetMapping(path = "/{catId}")
    public CategoryDto getCategory(@PathVariable long catId) {
        return categoryService.getCategory(catId);
    }

}
