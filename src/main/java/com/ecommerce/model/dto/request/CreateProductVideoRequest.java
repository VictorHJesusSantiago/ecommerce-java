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
public class CreateProductVideoRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200)
    private String title;

    @NotBlank(message = "URL is required")
    @Size(min = 1, max = 500)
    private String url;

    @Size(max = 500)
    private String thumbnailUrl;

    @Size(max = 20)
    private String type;

    private Integer duration;
    private int sortOrder;
    private boolean isActive;
}
