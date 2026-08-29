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
public class OrderShipmentResponse {
    private Long id;
    private Long orderId;
    private String orderNumber;
    private String trackingNumber;
    private String carrier;
    private String carrierUrl;
    private String status;
    private BigDecimal weight;
    private BigDecimal shippingCost;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime estimatedDeliveryAt;
    private String notes;
    private LocalDateTime createdAt;
}
