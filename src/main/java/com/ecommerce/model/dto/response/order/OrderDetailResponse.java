package com.ecommerce.model.dto.response.order;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private String orderNumber;
    private String status;
    private String paymentStatus;
    private String fulfillmentStatus;
    private BigDecimal subtotal;
    private BigDecimal shippingCost;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;
    private String currency;
    private String paymentMethod;
    private String paymentTransactionId;
    private boolean isPaid;
    private LocalDateTime paidAt;
    private boolean isShipped;
    private LocalDateTime shippedAt;
    private boolean isDelivered;
    private LocalDateTime deliveredAt;
    private boolean isCancelled;
    private LocalDateTime cancelledAt;
    private String cancelReason;
    private String notes;
    private String customerNote;
    private boolean isGift;
    private String giftMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private CustomerInfo customer;
    private AddressInfo shippingAddress;
    private AddressInfo billingAddress;
    private List<OrderItemInfo> items;
    private List<OrderStatusHistoryInfo> statusHistory;
    private List<OrderNoteInfo> notes;
    private ShippingInfo shipping;
    private PaymentInfo payment;
    private RefundInfo refund;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerInfo {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private String phone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String company;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private String phone;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemInfo {
        private Long id;
        private Long productId;
        private String productName;
        private String productSlug;
        private String productImage;
        private Long variantId;
        private String variantName;
        private String sku;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private BigDecimal discount;
        private BigDecimal tax;
        private String options;
        private String status;
        private boolean isRefundable;
        private BigDecimal refundedAmount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderStatusHistoryInfo {
        private Long id;
        private String status;
        private String note;
        private LocalDateTime createdAt;
        private String createdBy;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderNoteInfo {
        private Long id;
        private String note;
        private boolean isCustomerNote;
        private boolean isPublic;
        private LocalDateTime createdAt;
        private String createdBy;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippingInfo {
        private String method;
        private String carrier;
        private String trackingNumber;
        private BigDecimal cost;
        private String estimatedDelivery;
        private List<ShipmentInfo> shipments;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShipmentInfo {
        private Long id;
        private String trackingNumber;
        private String carrier;
        private String status;
        private LocalDateTime shippedAt;
        private LocalDateTime deliveredAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentInfo {
        private String method;
        private String status;
        private String transactionId;
        private BigDecimal amount;
        private String cardLast4;
        private String cardType;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundInfo {
        private String status;
        private BigDecimal amount;
        private String reason;
        private LocalDateTime processedAt;
    }
}
