package ru.practicum.explore.api.admin;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CategoryDto;
import ru.practicum.explore.commons.dto.NewCategoryDto;
import ru.practicum.explore.commons.model.Category;
import ru.practicum.explore.service.CategoryService;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto dto){
        return categoryService.update(dto);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto dto){
        return categoryService.add(dto);
    }

    @DeleteMapping(path = "/{cadId}")
    public String deleteCategory(@PathVariable long cadId){
        categoryService.delete(cadId);
        return "Категория удалена";
    }
    

}
