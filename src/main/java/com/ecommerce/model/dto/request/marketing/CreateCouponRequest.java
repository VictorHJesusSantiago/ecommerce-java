package com.ecommerce.model.dto.request.marketing;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCouponRequest {
    @NotBlank(message = "Code is required")
    private String code;

    private String description;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Value is required")
    @DecimalMin(value = "0.01", message = "Value must be greater than 0")
    private BigDecimal value;

    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscountAmount;
    private Integer usageLimit;
    private Integer usageLimitPerUser;
    private boolean isActive = true;
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
    private int priority;
    private List<Long> productIds;
    private List<Long> categoryIds;
    private List<Long> customerGroupIds;
}
