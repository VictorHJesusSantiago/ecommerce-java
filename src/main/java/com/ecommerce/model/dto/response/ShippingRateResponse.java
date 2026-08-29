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
public class ShippingRateResponse {
    private Long id;
    private Long shippingZoneId;
    private Long shippingMethodId;
    private String methodName;
    private String rateType;
    private BigDecimal minWeight;
    private BigDecimal maxWeight;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minQuantity;
    private Integer maxQuantity;
    private BigDecimal rate;
    private boolean freeShipping;
    private BigDecimal freeShippingMinAmount;
    private boolean isActive;
    private LocalDateTime createdAt;
}
