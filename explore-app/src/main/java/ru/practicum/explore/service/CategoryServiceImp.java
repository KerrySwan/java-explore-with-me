package ru.practicum.explore.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.commons.dto.CategoryDto;
import ru.practicum.explore.commons.dto.NewCategoryDto;
import ru.practicum.explore.commons.error.ConflictException;
import ru.practicum.explore.commons.mapper.CategoryMapper;
import ru.practicum.explore.commons.model.Category;
import ru.practicum.explore.repository.CategoryRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CategoryServiceImp implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto add(NewCategoryDto categoriesDto) {
        try {
            Category categories = categoryRepository.save(CategoryMapper.toModel(categoriesDto));
            return CategoryMapper.toDto(categories);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @Override
    public CategoryDto update(CategoryDto categoriesDto) {
        Category categories = categoryRepository.findById(categoriesDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Категория не найдена"));
        if (categoriesDto.getName() != null && !categoriesDto.getName().isEmpty()) {
            categories.setName(categoriesDto.getName());
        }
        try {
            categoryRepository.saveAndFlush(categories);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage());
        }
        return CategoryMapper.toDto(categories);
    }

    @Override
    public void delete(long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategory(long catId) {
        Category categories = categoryRepository.findById(catId)
                .orElseThrow(() -> new EntityNotFoundException("Категория не найдена"));
        return CategoryMapper.toDto(categories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAll(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id")))
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());

    }


}
