package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
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
public class CreatePriceRuleRequest {

    @NotBlank(message = "Rule name is required")
    @Size(min = 2, max = 200)
    private String name;

    @Size(max = 500)
    private String description;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Value is required")
    @DecimalMin(value = "0.00")
    private BigDecimal value;

    private String appliesTo;
    private String targetType;

    @Min(value = 1)
    private Integer minimumQuantity;

    @DecimalMin(value = "0.00")
    private BigDecimal minimumAmount;

    @Min(value = 0)
    private Integer maxUses;

    private String customerEligibility;

    private LocalDateTime startsAt;
    private LocalDateTime endsAt;

    private boolean isActive;
    private int priority;
}
