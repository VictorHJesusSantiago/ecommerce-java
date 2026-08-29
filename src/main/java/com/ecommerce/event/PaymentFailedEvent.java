package com.ecommerce.event;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentFailedEvent {

    private Long orderId;
    private String orderNumber;
    private java.math.BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String errorMessage;
    private java.time.LocalDateTime failedAt;
}
