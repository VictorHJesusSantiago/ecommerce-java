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
public class ReturnProcessedEvent {
    private Long returnId;
    private String returnNumber;
    private Long orderId;
    private Long customerId;
    private String status;
    private BigDecimal refundAmount;
    private LocalDateTime processedAt;
}
