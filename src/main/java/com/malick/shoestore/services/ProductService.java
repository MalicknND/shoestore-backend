package com.malick.shoestore.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.malick.shoestore.dtos.CreateProductRequest;
import com.malick.shoestore.dtos.ProductResponse;
import com.malick.shoestore.dtos.UpdateProductRequest;

public interface ProductService {

    ProductResponse create(CreateProductRequest request);

    Page<ProductResponse> findAll(Pageable pageable);

    ProductResponse findById(Long id);

    ProductResponse update(Long id, UpdateProductRequest request);

    void delete(Long id);
}
