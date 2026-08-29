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
public class OrderSummaryResponse {
    private String orderNumber;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal shippingCost;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;
    private String currency;
    private String paymentMethod;
    private boolean isPaid;
    private String shippingMethod;
    private String estimatedDelivery;
    private int itemCount;
    private LocalDateTime createdAt;
}
