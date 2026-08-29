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
public class CreateShippingMethodRequest {

    @NotBlank(message = "Method name is required")
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 100)
    private String carrier;

    @NotNull(message = "Rate type is required")
    private String rateType;

    @DecimalMin(value = "0.00")
    private BigDecimal rate;

    private boolean freeShipping;

    @DecimalMin(value = "0.00")
    private BigDecimal freeShippingMinAmount;

    private Integer estimatedDays;

    @Size(max = 200)
    private String estimatedDeliveryText;

    private boolean isActive;

    private int sortOrder;
}
