package com.malick.shoestore.services;

import java.util.List;

import com.malick.shoestore.dtos.CategoryResponse;
import com.malick.shoestore.dtos.CreateCategoryRequest;
import com.malick.shoestore.dtos.UpdateCategoryRequest;

public interface CategoryService {

    CategoryResponse create(CreateCategoryRequest request);

    List<CategoryResponse> findAll();

    CategoryResponse findById(Long id);

    CategoryResponse update(Long id, UpdateCategoryRequest request);

    void delete(Long id);
}
