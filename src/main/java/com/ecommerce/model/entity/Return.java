package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "returns", indexes = {
    @Index(name = "idx_return_number", columnList = "returnNumber", unique = true),
    @Index(name = "idx_return_order", columnList = "order_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String returnNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String status = "REQUESTED";

    private String reason;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String adminNote;

    @Column(nullable = false)
    private boolean isReturnShippingProvided = false;

    private String returnTrackingNumber;

    private String returnCarrier;

    @Column(precision = 12, scale = 2)
    private BigDecimal refundAmount = BigDecimal.ZERO;

    @Column(precision = 12, scale = 2)
    private BigDecimal restockingFee = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean isRefunded = false;

    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime receivedAt;

    private LocalDateTime inspectedAt;

    private LocalDateTime refundedAt;

    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    private User approvedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
