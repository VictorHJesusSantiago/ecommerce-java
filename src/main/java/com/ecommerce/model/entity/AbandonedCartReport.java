package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "abandoned_cart_reports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbandonedCartReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "email")
    private String email;

    @Column(name = "items_count")
    private Integer itemsCount;

    @Column(name = "cart_value", precision = 10, scale = 2)
    private BigDecimal cartValue;

    @Column(name = "recovered")
    private boolean recovered;

    @Column(name = "recovered_at")
    private LocalDateTime recoveredAt;

    @Column(name = "reminder_sent")
    private boolean reminderSent;

    @Column(name = "reminder_sent_at")
    private LocalDateTime reminderSentAt;

    @Column(name = "reminder_count")
    private int reminderCount;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
