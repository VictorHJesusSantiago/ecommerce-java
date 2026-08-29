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
public class CreateShippingZoneRequest {

    @NotBlank(message = "Zone name is required")
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Country is required")
    private String country;

    private String state;
    private String zipCode;
    private String city;

    @DecimalMin(value = "0.00")
    private BigDecimal minOrderAmount;

    private boolean isActive;

    private int sortOrder;
}
