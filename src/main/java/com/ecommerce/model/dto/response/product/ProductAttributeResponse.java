package com.ecommerce.model.dto.response.product;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductAttributeResponse {

    private Long id;
    private String attributeName;
    private String attributeValue;
    private String attributeGroup;
    private int sortOrder;
    private boolean isFilterable;
    private boolean isSearchable;
    private boolean isVariantOption;
}
