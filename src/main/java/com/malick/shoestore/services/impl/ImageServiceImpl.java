package com.malick.shoestore.services.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.malick.shoestore.dtos.ImageUploadResponse;
import com.malick.shoestore.exceptions.ImageUploadException;
import com.malick.shoestore.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/png", "image/jpeg", "image/webp");
    private static final String PRODUCT_IMAGE_FOLDER = "shoestore/products";

    private final Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public ImageUploadResponse uploadImage(MultipartFile file) {
        validateImage(file);

        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", PRODUCT_IMAGE_FOLDER,
                            "resource_type", "image"
                    )
            );

            String secureUrl = requireCloudinaryValue(result, "secure_url");
            String publicId = requireCloudinaryValue(result, "public_id");

            return new ImageUploadResponse(secureUrl, publicId);
        } catch (IOException exception) {
            throw new ImageUploadException("Unable to read image file", exception);
        } catch (RuntimeException exception) {
            throw new ImageUploadException("Unable to upload image", exception);
        }
    }

    @Override
    public void deleteImage(String publicId) {
        if (publicId == null || publicId.isBlank()) {
            return;
        }

        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException exception) {
            throw new ImageUploadException("Unable to delete image", exception);
        }
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Only PNG, JPEG and WEBP images are allowed");
        }
    }

    private String requireCloudinaryValue(Map<?, ?> result, String key) {
        Object value = result.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new ImageUploadException("Cloudinary response is missing " + key);
        }
        return value.toString();
    }
}
