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
public class UserRewardResponse {
    private Long id;
    private Long customerId;
    private String type;
    private String name;
    private String description;
    private Long points;
    private BigDecimal currencyValue;
    private String status;
    private String sourceType;
    private Long sourceId;
    private LocalDateTime expiresAt;
    private LocalDateTime redeemedAt;
    private boolean isExpired;
    private LocalDateTime createdAt;
}
