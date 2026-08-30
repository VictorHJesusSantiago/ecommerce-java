package com.ecommerce.model.entity;

import com.ecommerce.model.enums.ReturnStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "return_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class ReturnRequest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @Column(name = "return_number", nullable = false, unique = true, length = 30)
    private String returnNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private ReturnStatus status = ReturnStatus.REQUESTED;

    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "refund_amount", precision = 10, scale = 2)
    private BigDecimal refundAmount;

    @Column(name = "return_shipping_label", length = 500)
    private String returnShippingLabel;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "admin_notes", length = 1000)
    private String adminNotes;
}
