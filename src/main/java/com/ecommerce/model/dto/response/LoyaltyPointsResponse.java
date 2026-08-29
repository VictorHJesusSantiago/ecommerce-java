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
public class LoyaltyPointsResponse {
    private Long id;
    private Long customerId;
    private long points;
    private String type;
    private String description;
    private long balanceAfter;
    private Long orderId;
    private String orderNumber;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
