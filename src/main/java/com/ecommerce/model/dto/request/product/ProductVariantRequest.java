package com.ecommerce.model.dto.request.product;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariantRequest {

    private Long id;

    @NotBlank(message = "Variant name is required")
    private String name;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String barcode;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    private BigDecimal compareAtPrice;

    private BigDecimal costPrice;

    @Min(value = 0, message = "Stock quantity must be non-negative")
    private int stockQuantity;

    private boolean trackInventory;

    private boolean isActive;

    private Double weight;

    private String weightUnit;

    private String imageUrl;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String option5;
}
