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
public class ReturnResponse {
    private Long id;
    private String returnNumber;
    private Long orderId;
    private String orderNumber;
    private String status;
    private String reason;
    private String rejectionReason;
    private BigDecimal totalRefundAmount;
    private BigDecimal refundAmount;
    private String refundMethod;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;
}
