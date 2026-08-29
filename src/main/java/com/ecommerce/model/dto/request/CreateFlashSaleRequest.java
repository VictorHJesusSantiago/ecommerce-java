package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
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
public class CreateFlashSaleRequest {

    @NotBlank(message = "Sale name is required")
    @Size(min = 2, max = 200)
    private String name;

    @Size(max = 1000)
    private String description;

    @Size(max = 500)
    private String bannerUrl;

    @NotNull(message = "Start time is required")
    private LocalDateTime startsAt;

    @NotNull(message = "End time is required")
    private LocalDateTime endsAt;

    private Integer maxQuantityPerProduct;
    private boolean isActive;
}
