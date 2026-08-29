package com.ecommerce.event;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderStatusChangedEvent {

    private Long orderId;
    private String orderNumber;
    private Long userId;
    private String previousStatus;
    private String newStatus;
    private String trackingNumber;
    private java.time.LocalDateTime changedAt;
}
