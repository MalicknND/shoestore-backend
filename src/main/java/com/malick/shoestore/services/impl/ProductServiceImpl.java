package com.malick.shoestore.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.malick.shoestore.dtos.CreateProductRequest;
import com.malick.shoestore.dtos.ImageUploadResponse;
import com.malick.shoestore.dtos.ProductResponse;
import com.malick.shoestore.dtos.UpdateProductRequest;
import com.malick.shoestore.entities.Category;
import com.malick.shoestore.entities.Product;
import com.malick.shoestore.entities.ProductImage;
import com.malick.shoestore.exceptions.CategoryNotFoundException;
import com.malick.shoestore.exceptions.ProductImageNotFoundException;
import com.malick.shoestore.exceptions.ProductNotFoundException;
import com.malick.shoestore.mappers.ProductMapper;
import com.malick.shoestore.repositories.CategoryRepository;
import com.malick.shoestore.repositories.ProductImageRepository;
import com.malick.shoestore.repositories.ProductRepository;
import com.malick.shoestore.services.ImageService;
import com.malick.shoestore.services.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ImageService imageService;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductImageRepository productImageRepository,
            CategoryRepository categoryRepository,
            ProductMapper productMapper,
            ImageService imageService
    ) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.imageService = imageService;
    }

    @Override
    public ProductResponse create(CreateProductRequest request) {
        Category category = getCategoryOrThrow(request.categoryId());
        Product product = productMapper.toEntity(request, category);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return productMapper.toResponse(getProductOrThrow(id));
    }

    @Override
    public ProductResponse update(Long id, UpdateProductRequest request) {
        Product product = getProductOrThrow(id);
        Category category = getCategoryOrThrow(request.categoryId());

        product.setName(request.name().trim());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setStatus(request.status());
        product.setCategory(category);
        product.replaceImages(productMapper.toImages(request.imageUrls()));

        return productMapper.toResponse(product);
    }

    @Override
    public void delete(Long id) {
        Product product = getProductOrThrow(id);
        productRepository.delete(product);
    }

    @Override
    public ProductResponse uploadImage(Long productId, MultipartFile file) {
        Product product = getProductOrThrow(productId);
        ImageUploadResponse uploadedImage = imageService.uploadImage(file);

        ProductImage productImage = ProductImage.builder()
                .imageUrl(uploadedImage.secureUrl())
                .publicId(uploadedImage.publicId())
                .build();

        product.addImage(productImage);
        productImageRepository.save(productImage);

        return productMapper.toResponse(product);
    }

    @Override
    public void deleteImage(Long imageId) {
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ProductImageNotFoundException(imageId));

        imageService.deleteImage(productImage.getPublicId());
        productImageRepository.delete(productImage);
    }

    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private Category getCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
