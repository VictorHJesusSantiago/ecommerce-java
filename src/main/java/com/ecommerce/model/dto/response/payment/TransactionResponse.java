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
public class TransactionResponse {
    private Long id;
    private Long orderId;
    private String orderNumber;
    private String transactionNumber;
    private String paymentGateway;
    private String paymentMethod;
    private String status;
    private BigDecimal amount;
    private String currency;
    private BigDecimal fee;
    private BigDecimal netAmount;
    private String gatewayTransactionId;
    private String cardLast4;
    private String cardType;
    private boolean isRefunded;
    private BigDecimal refundedAmount;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
}
