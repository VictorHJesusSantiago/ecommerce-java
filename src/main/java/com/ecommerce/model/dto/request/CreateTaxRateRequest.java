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
public class CreateTaxRateRequest {

    @NotBlank(message = "Tax name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotNull(message = "Country is required")
    private String country;

    private String state;
    private String zipCode;
    private String city;

    @NotNull(message = "Rate is required")
    @DecimalMin(value = "0.00", message = "Rate must be at least 0")
    @DecimalMax(value = "100.00", message = "Rate must be at most 100")
    private BigDecimal rate;

    private boolean isCompound;
    private boolean isActive;
    private boolean includeTaxInPrice;

    private int priority;

    @Size(max = 20)
    private String type;
}
