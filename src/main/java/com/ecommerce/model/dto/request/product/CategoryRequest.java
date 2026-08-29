package com.ecommerce.model.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryRequest {

    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;

    private String slug;

    private String description;

    private Long parentId;

    private String imageUrl;

    private String iconUrl;

    private String bannerUrl;

    private String color;

    private boolean isActive;

    private boolean isVisible;

    private int sortOrder;

    private String metaTitle;

    private String metaDescription;

    private String metaKeywords;
}
