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
public class BrandRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String slug;
    private String description;
    private String logo;
    private String banner;
    private String website;
    private boolean isActive;
    private boolean isFeatured;
    private int sortOrder;
    private String metaTitle;
    private String metaDescription;
}
