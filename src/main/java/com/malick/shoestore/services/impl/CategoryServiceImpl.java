package com.malick.shoestore.services.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malick.shoestore.dtos.CategoryResponse;
import com.malick.shoestore.dtos.CreateCategoryRequest;
import com.malick.shoestore.dtos.UpdateCategoryRequest;
import com.malick.shoestore.entities.Category;
import com.malick.shoestore.exceptions.CategoryNotFoundException;
import com.malick.shoestore.mappers.CategoryMapper;
import com.malick.shoestore.repositories.CategoryRepository;
import com.malick.shoestore.services.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {
        ensureNameIsAvailable(request.name());
        Category category = categoryRepository.save(categoryMapper.toEntity(request));
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        return categoryMapper.toResponse(getCategoryOrThrow(id));
    }

    @Override
    public CategoryResponse update(Long id, UpdateCategoryRequest request) {
        Category category = getCategoryOrThrow(id);

        if (!category.getName().equalsIgnoreCase(request.name())) {
            ensureNameIsAvailable(request.name());
        }

        category.setName(request.name().trim());
        return categoryMapper.toResponse(category);
    }

    @Override
    public void delete(Long id) {
        Category category = getCategoryOrThrow(id);
        categoryRepository.delete(category);
    }

    private Category getCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    private void ensureNameIsAvailable(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name.trim())) {
            throw new IllegalArgumentException("Category name already exists");
        }
    }
}
