package com.ecommerce.model.dto.response.product;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String shortDescription;
    private String sku;
    private String barcode;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private BigDecimal costPrice;
    private BigDecimal taxRate;
    private boolean isTaxable;
    private boolean isInclusiveTax;
    private String status;
    private boolean isActive;
    private boolean isFeatured;
    private boolean isDigital;
    private boolean requiresShipping;
    private boolean trackInventory;
    private Double weight;
    private String weightUnit;
    private Double length;
    private Double width;
    private Double height;
    private String dimensionUnit;
    private String metaTitle;
    private String metaDescription;
    private Double averageRating;
    private Long reviewCount;
    private Long totalSold;
    private Long viewCount;
    private Long wishlistCount;
    private List<ProductImageResponse> images;
    private List<ProductVariantResponse> variants;
    private List<ProductAttributeResponse> attributes;
    private List<TagResponse> tags;
    private List<CollectionResponse> collections;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
}
