package com.ecommerce.model.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBundleResponse {
    private Long id;
    private String name;
    private String description;
    private String slug;
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private String imageUrl;
    private boolean isActive;
    private Integer maxQuantity;
    private long totalSold;
    private int itemsCount;
    private LocalDateTime createdAt;
}
