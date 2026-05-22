package com.malick.shoestore.dtos;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresIn
) {
}
