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
public class CreateLoyaltyTierRequest {

    @NotBlank(message = "Tier name is required")
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Minimum points is required")
    @Min(value = 0)
    private Long minimumPoints;

    @NotNull(message = "Maximum points is required")
    private Long maximumPoints;

    private BigDecimal pointsMultiplier;

    private String benefits;

    private Integer priority;
    private boolean isActive;
}
