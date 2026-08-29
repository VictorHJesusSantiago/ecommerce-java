package com.ecommerce.model.dto.request.product;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateProductRequest {

    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    private String slug;

    private String description;

    private String shortDescription;

    private String sku;

    private Long categoryId;

    private Long brandId;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    private BigDecimal compareAtPrice;

    private BigDecimal costPrice;

    private BigDecimal taxRate;

    private Boolean isTaxable;

    private Boolean isInclusiveTax;

    private String status;

    private Boolean isActive;

    private Boolean isFeatured;

    private Boolean isDigital;

    private Boolean requiresShipping;

    private Boolean trackInventory;

    private Boolean allowBackorder;

    private Integer minOrderQuantity;

    private Integer maxOrderQuantity;

    private Double weight;

    private String weightUnit;

    private Double length;

    private Double width;

    private Double height;

    private String dimensionUnit;

    private String metaTitle;

    private String metaDescription;

    private String metaKeywords;

    private List<Long> tagIds;

    private List<Long> collectionIds;

    private List<ProductVariantRequest> variants;

    private List<ProductAttributeRequest> attributes;

    private List<ProductImageRequest> images;

    private Map<String, String> options;
}
