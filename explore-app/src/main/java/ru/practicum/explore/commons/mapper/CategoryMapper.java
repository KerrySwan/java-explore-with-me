package ru.practicum.explore.commons.mapper;

import ru.practicum.explore.commons.dto.CategoryDto;
import ru.practicum.explore.commons.dto.NewCategoryDto;
import ru.practicum.explore.commons.model.Category;

public class CategoryMapper {

    public static Category toModel(CategoryDto dto){
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static CategoryDto toDto(Category model){
        return CategoryDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    public static Category toModel(NewCategoryDto dto){
        return Category.builder()
                .name(dto.getName())
                .build();
    }

}
