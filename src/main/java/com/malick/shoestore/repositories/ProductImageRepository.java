package com.malick.shoestore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malick.shoestore.entities.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
