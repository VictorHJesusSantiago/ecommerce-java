package com.ecommerce.model.dto.response.product;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariantResponse {

    private Long id;
    private String name;
    private String sku;
    private String barcode;
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private int stockQuantity;
    private int reservedQuantity;
    private int availableQuantity;
    private boolean trackInventory;
    private boolean isActive;
    private Double weight;
    private String imageUrl;
    private String option1;
    private String option2;
    private String option3;
    private List<ProductImageResponse> images;
}
