package com.ecommerce.event;

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
public class ReturnCreatedEvent {
    private Long returnId;
    private String returnNumber;
    private Long orderId;
    private String orderNumber;
    private Long customerId;
    private BigDecimal totalAmount;
    private String reason;
    private LocalDateTime createdAt;
}
