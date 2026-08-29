package com.ecommerce.model.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String slug;
    private String description;
    private String imageUrl;
    private String bannerUrl;
    private Long parentId;
    private int sortOrder;
    private boolean isActive;
    private boolean isFeatured;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
}
