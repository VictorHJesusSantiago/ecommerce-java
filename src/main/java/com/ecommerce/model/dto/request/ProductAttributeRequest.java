package com.ecommerce.model.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String displayName;
    private String type;
    private String value;
    private String options;
    private boolean isRequired;
    private boolean isVisible;
    private boolean isFilterable;
    private boolean isSearchable;
    private int sortOrder;
}
