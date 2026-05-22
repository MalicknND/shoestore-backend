package com.malick.shoestore.dtos;

import java.time.Instant;

public record CategoryResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}
