package com.malick.shoestore.dtos;

public record ProductImageResponse(
        Long id,
        String imageUrl,
        String publicId
) {
}
