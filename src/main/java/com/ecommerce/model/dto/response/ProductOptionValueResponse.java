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
public class ProductOptionValueResponse {
    private Long id;
    private Long optionId;
    private String optionName;
    private String value;
    private String displayValue;
    private int sortOrder;
    private boolean isActive;
    private LocalDateTime createdAt;
}
