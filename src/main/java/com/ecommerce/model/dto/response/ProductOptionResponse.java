package com.ecommerce.model.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionResponse {
    private Long id;
    private String name;
    private String displayName;
    private String type;
    private boolean isRequired;
    private int sortOrder;
    private boolean isActive;
    private int valuesCount;
    private LocalDateTime createdAt;
}
