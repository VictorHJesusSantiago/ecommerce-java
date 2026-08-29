package com.ecommerce.model.dto.response.marketing;

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
public class DiscountResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
    private String type;
    private BigDecimal value;
    private BigDecimal minimumPurchaseAmount;
    private BigDecimal maximumDiscountAmount;
    private Integer buyQuantity;
    private Integer getQuantity;
    private boolean isActive;
    private boolean isAutomatic;
    private boolean useCouponCode;
    private boolean appliesToAllProducts;
    private LocalDateTime startsAt;
    private LocalDateTime expiresAt;
    private int priority;
    private long usageCount;
    private long usageLimit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
