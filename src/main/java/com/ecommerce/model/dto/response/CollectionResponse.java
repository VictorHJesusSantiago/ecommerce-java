package com.ecommerce.model.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private String bannerUrl;
    private boolean isActive;
    private boolean isFeatured;
    private int sortOrder;
    private long productsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
