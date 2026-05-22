package com.malick.shoestore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malick.shoestore.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);
}
