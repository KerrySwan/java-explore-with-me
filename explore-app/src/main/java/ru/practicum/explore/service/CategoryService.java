package ru.practicum.explore.service;

import ru.practicum.explore.commons.dto.CategoryDto;
import ru.practicum.explore.commons.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto add(NewCategoryDto categoriesDto);

    CategoryDto update(CategoryDto categoryDto);

    void delete(long catId);

    CategoryDto getCategory(long catId);

    List<CategoryDto> getAll(int from, int size);

}
