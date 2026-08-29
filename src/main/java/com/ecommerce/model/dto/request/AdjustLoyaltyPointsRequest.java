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
public class AdjustLoyaltyPointsRequest {

    @NotNull(message = "Points are required")
    private Long points;

    @NotBlank(message = "Type is required")
    private String type;

    @Size(max = 500)
    private String description;

    private Long orderId;
}
