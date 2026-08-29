package com.ecommerce.model.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageRequest {
    private Long productId;
    private String url;
    private String thumbnailUrl;
    private String altText;
    private int sortOrder;
    private boolean isPrimary;
}
