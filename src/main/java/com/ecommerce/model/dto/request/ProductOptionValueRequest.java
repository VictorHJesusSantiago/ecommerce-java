package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionValueRequest {

    @NotNull(message = "Option ID is required")
    private Long optionId;

    @NotBlank(message = "Value is required")
    @Size(min = 1, max = 200)
    private String value;

    @Size(max = 200)
    private String displayValue;

    private int sortOrder;
    private boolean isActive;
}
