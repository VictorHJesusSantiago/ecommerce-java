package com.ecommerce.event;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderCreatedEvent {

    private Long orderId;
    private String orderNumber;
    private Long userId;
    private String userEmail;
    private String userName;
    private java.math.BigDecimal totalAmount;
    private String currency;
    private String paymentMethod;
    private String source;
    private java.time.LocalDateTime createdAt;
}
