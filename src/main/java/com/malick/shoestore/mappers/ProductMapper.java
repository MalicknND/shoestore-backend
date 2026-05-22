package com.malick.shoestore.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.malick.shoestore.dtos.CreateProductRequest;
import com.malick.shoestore.dtos.ProductResponse;
import com.malick.shoestore.entities.Category;
import com.malick.shoestore.entities.Product;
import com.malick.shoestore.entities.ProductImage;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public Product toEntity(CreateProductRequest request, Category category) {
        Product product = Product.builder()
                .name(request.name().trim())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .status(request.status())
                .category(category)
                .build();

        product.replaceImages(toImages(request.imageUrls()));
        return product;
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                categoryMapper.toResponse(product.getCategory()),
                product.getImages().stream().map(ProductImage::getImageUrl).toList(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public List<ProductImage> toImages(List<String> imageUrls) {
        return Optional.ofNullable(imageUrls).orElseGet(List::of)
                .stream()
                .map(String::trim)
                .filter(imageUrl -> !imageUrl.isBlank())
                .map(imageUrl -> ProductImage.builder().imageUrl(imageUrl).build())
                .toList();
    }
}
