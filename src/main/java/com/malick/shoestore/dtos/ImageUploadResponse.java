package com.malick.shoestore.dtos;

public record ImageUploadResponse(
        String secureUrl,
        String publicId
) {
}
