package com.ecommerce.model.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BrandRequest {

    private Long id;

    @NotBlank(message = "Brand name is required")
    private String name;

    private String slug;

    private String description;

    private String logoUrl;

    private String bannerUrl;

    private String websiteUrl;

    private String metaTitle;

    private String metaDescription;

    private boolean isActive;

    private boolean isFeatured;

    private int sortOrder;
}
