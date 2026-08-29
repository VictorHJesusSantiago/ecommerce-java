package com.ecommerce.model.dto.response.order;

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
public class OrderListResponse {
    private Long id;
    private String orderNumber;
    private String status;
    private String paymentStatus;
    private String fulfillmentStatus;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String currency;
    private String customerName;
    private String customerEmail;
    private int itemCount;
    private boolean isPaid;
    private boolean isShipped;
    private boolean isDelivered;
    private boolean isCancelled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
