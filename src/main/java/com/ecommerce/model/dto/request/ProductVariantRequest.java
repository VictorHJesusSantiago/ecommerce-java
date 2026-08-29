package com.ecommerce.model.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String sku;
    private String barcode;
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private BigDecimal costPrice;
    private Integer stockQuantity;
    private Integer lowStockThreshold;
    private String weight;
    private String weightUnit;
    private String dimensions;
    private String dimensionUnit;
    private String color;
    private String size;
    private String material;
    private Map<String, String> options;
    private boolean isActive;
    private boolean isDefault;
    private String imageUrl;
}
