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
public class CreateDiscountRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String code;
    private String description;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Value is required")
    private BigDecimal value;

    private BigDecimal minimumPurchaseAmount;
    private BigDecimal maximumDiscountAmount;
    private Integer buyQuantity;
    private Integer getQuantity;
    private boolean isActive = true;
    private boolean isAutomatic;
    private boolean useCouponCode;
    private boolean appliesToAllProducts;
    private LocalDateTime startsAt;
    private LocalDateTime expiresAt;
    private int priority;
    private List<Long> productIds;
    private List<Long> categoryIds;
    private List<Long> collectionIds;
}
