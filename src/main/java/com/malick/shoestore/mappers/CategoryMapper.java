package com.malick.shoestore.mappers;

import org.springframework.stereotype.Component;

import com.malick.shoestore.dtos.CategoryResponse;
import com.malick.shoestore.dtos.CreateCategoryRequest;
import com.malick.shoestore.entities.Category;

@Component
public class CategoryMapper {

    public Category toEntity(CreateCategoryRequest request) {
        return Category.builder()
                .name(request.name().trim())
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
