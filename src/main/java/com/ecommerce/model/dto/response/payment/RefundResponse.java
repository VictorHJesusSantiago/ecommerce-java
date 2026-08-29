package com.ecommerce.model.dto.response.payment;

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
public class RefundResponse {
    private Long id;
    private String refundNumber;
    private Long orderId;
    private String orderNumber;
    private String status;
    private BigDecimal amount;
    private String currency;
    private String reason;
    private String note;
    private String refundMethod;
    private String gatewayRefundId;
    private boolean isSuccessful;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
}
