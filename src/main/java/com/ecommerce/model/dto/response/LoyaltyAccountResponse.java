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
public class LoyaltyAccountResponse {
    private Long id;
    private Long customerId;
    private long pointsBalance;
    private long lifetimePoints;
    private String tier;
    private boolean isActive;
    private LocalDateTime tierUpdatedAt;
    private LocalDateTime pointsExpiryDate;
    private LocalDateTime createdAt;
}
