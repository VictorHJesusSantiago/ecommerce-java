package com.ecommerce.model.dto.response.order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {

    private Long id;
    private String orderNumber;
    private String status;
    private String paymentStatus;
    private String paymentMethod;
    private List<OrderItemResponse> items;
    private int itemCount;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal shippingAmount;
    private BigDecimal discountAmount;
    private BigDecimal couponDiscount;
    private BigDecimal total;
    private String currency;
    private String shippingMethod;
    private String trackingNumber;
    private String trackingUrl;
    private String carrier;
    private AddressResponse shippingAddress;
    private AddressResponse billingAddress;
    private String customerNote;
    private boolean isPaid;
    private boolean isFulfilled;
    private boolean isCancelled;
    private String cancelReason;
    private List<OrderStatusHistoryResponse> statusHistory;
    private List<OrderNoteResponse> notes;
    private LocalDateTime paidAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime createdAt;
}
