package com.ecommerce.model.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductAttributeRequest {

    private Long id;

    @NotBlank(message = "Attribute name is required")
    private String attributeName;

    @NotBlank(message = "Attribute value is required")
    private String attributeValue;

    private String attributeGroup;

    private int sortOrder;

    private boolean isFilterable;

    private boolean isSearchable;

    private boolean isVariantOption;
}
