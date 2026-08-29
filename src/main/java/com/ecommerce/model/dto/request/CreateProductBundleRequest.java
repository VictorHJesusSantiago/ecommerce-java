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
public class CreateProductBundleRequest {

    @NotBlank(message = "Bundle name is required")
    @Size(min = 2, max = 200)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotBlank(message = "Slug is required")
    @Size(min = 2, max = 200)
    private String slug;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @DecimalMin(value = "0.00")
    private BigDecimal compareAtPrice;

    private String imageUrl;
    private boolean isActive;
    private Integer maxQuantity;
}
