package com.malick.shoestore.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.malick.shoestore.enums.ProductStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
        @NotBlank(message = "Product name is required")
        @Size(max = 180, message = "Product name must not exceed 180 characters")
        String name,

        @Size(max = 5000, message = "Description must not exceed 5000 characters")
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock must be zero or positive")
        Integer stock,

        @NotNull(message = "Status is required")
        ProductStatus status,

        @NotNull(message = "Category id is required")
        @Positive(message = "Category id must be positive")
        Long categoryId,

        List<@NotBlank(message = "Image URL must not be blank") String> imageUrls
) {
}
