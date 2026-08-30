package com.ecommerce.model.entity;

import com.ecommerce.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions", indexes = {
    @Index(name = "idx_tx_order", columnList = "order_id"),
    @Index(name = "idx_tx_status", columnList = "status"),
    @Index(name = "idx_tx_reference", columnList = "referenceNumber")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, length = 50)
    private String transactionNumber;

    private String referenceNumber;

    @Column(nullable = false, length = 50)
    private String paymentGateway;

    @Column(nullable = false, length = 50)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(length = 3)
    private String currency = "USD";

    private BigDecimal exchangeRate;

    private BigDecimal fee;

    private BigDecimal netAmount;

    private String gatewayTransactionId;

    private String gatewayResponseCode;

    @Column(columnDefinition = "TEXT")
    private String gatewayResponseMessage;

    @Column(columnDefinition = "TEXT")
    private String gatewayRawResponse;

    @Column(columnDefinition = "TEXT")
    private String paymentToken;

    private String cardLast4;

    private String cardType;

    private String cardExpMonth;

    private String cardExpYear;

    private String billingEmail;

    private String billingName;

    private String ipAddress;

    private String三维DSStatus;

    private String riskScore;

    @Column(nullable = false)
    private boolean isRefunded = false;

    private BigDecimal refundedAmount;

    private String refundReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by_id")
    private User processedBy;

    private LocalDateTime processedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
