package ru.practicum.explore.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CategoryDto;
import ru.practicum.explore.commons.dto.NewCategoryDto;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto dto){
        return null;
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto dto){
        return null;
    }

    @DeleteMapping(path = "/{cadId}")
    public String deleteCategory(@PathVariable long cadId){
        return "Категория удалена";
    }
    

}
