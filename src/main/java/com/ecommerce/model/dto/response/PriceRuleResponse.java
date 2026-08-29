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
public class PriceRuleResponse {
    private Long id;
    private String name;
    private String description;
    private String type;
    private BigDecimal value;
    private String appliesTo;
    private String targetType;
    private Integer minimumQuantity;
    private BigDecimal minimumAmount;
    private Integer maxUses;
    private long usageCount;
    private String customerEligibility;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private boolean isActive;
    private int priority;
    private LocalDateTime createdAt;
}
