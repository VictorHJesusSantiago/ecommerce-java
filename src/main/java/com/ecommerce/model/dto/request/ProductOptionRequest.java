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
public class ProductOptionRequest {

    @NotBlank(message = "Option name is required")
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 100)
    private String displayName;

    @Size(max = 20)
    private String type;

    private boolean isRequired;
    private int sortOrder;
    private boolean isActive;
}
