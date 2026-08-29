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
public class CouponResponse {
    private Long id;
    private String code;
    private String description;
    private String type;
    private BigDecimal value;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscountAmount;
    private Integer usageLimit;
    private Integer usageLimitPerUser;
    private long usedCount;
    private long currentUsageCount;
    private boolean isActive;
    private boolean isAutomatic;
    private boolean combineWithOtherCoupons;
    private boolean combineWithProductDiscounts;
    private boolean appliesToAllProducts;
    private Integer buyXQuantity;
    private Integer getYQuantity;
    private BigDecimal getYDiscount;
    private boolean startsAtEnabled;
    private boolean expiresAtEnabled;
    private LocalDateTime startsAt;
    private LocalDateTime expiresAt;
    private boolean isValid;
    private int priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
