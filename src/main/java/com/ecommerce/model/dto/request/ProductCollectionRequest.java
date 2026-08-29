package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCollectionRequest {

    @NotBlank(message = "Collection name is required")
    @Size(min = 2, max = 200)
    private String name;

    @Size(max = 1000)
    private String description;

    @Size(max = 200)
    private String slug;

    private String type;

    private String imageUrl;

    private String bannerUrl;

    private boolean isActive;

    private Integer sortOrder;

    @Size(max = 200)
    private String filterCriteria;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;
}
