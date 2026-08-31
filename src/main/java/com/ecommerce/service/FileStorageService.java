package com.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileStorageService {

    String storeFile(MultipartFile file, String directory);

    byte[] getFile(String filePath);

    void deleteFile(String filePath);

    String storeProductImage(Long productId, MultipartFile file);

    String storeUserAvatar(Long userId, MultipartFile file);

    String generatePresignedUrl(String filePath, int expirationMinutes);

    Map<String, Object> getFileMetadata(String filePath);
}
