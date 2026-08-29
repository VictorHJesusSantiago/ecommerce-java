package com.ecommerce.event;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentCompletedEvent {

    private Long orderId;
    private String orderNumber;
    private Long transactionId;
    private String transactionNumber;
    private java.math.BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String gatewayTransactionId;
    private java.time.LocalDateTime completedAt;
}
