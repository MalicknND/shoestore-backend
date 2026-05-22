package com.malick.shoestore.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.malick.shoestore.enums.ProductStatus;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        ProductStatus status,
        CategoryResponse category,
        List<String> imageUrls,
        Instant createdAt,
        Instant updatedAt
) {
}
