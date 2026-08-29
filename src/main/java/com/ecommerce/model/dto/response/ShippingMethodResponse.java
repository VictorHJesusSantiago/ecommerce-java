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
public class ShippingMethodResponse {
    private Long id;
    private String name;
    private String description;
    private String carrier;
    private BigDecimal rate;
    private BigDecimal freeShippingMinAmount;
    private Integer estimatedDays;
    private String estimatedDeliveryText;
    private boolean isActive;
    private boolean isFreeShipping;
    private boolean isFlatRate;
    private boolean isPickup;
    private int sortOrder;
    private LocalDateTime createdAt;
}
