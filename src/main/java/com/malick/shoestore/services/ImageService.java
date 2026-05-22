package com.malick.shoestore.services;

import org.springframework.web.multipart.MultipartFile;

import com.malick.shoestore.dtos.ImageUploadResponse;

public interface ImageService {

    ImageUploadResponse uploadImage(MultipartFile file);

    void deleteImage(String publicId);
}
