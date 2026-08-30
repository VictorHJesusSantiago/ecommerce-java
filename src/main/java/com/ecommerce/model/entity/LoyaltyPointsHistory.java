package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loyalty_points_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoyaltyPointsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private String type;

    private String description;

    private String referenceType;

    private Long referenceId;

    private String referenceNumber;

    @Column(precision = 12, scale = 2)
    private BigDecimal orderAmount;

    private String expiresAt;

    private boolean isExpired = false;

    private boolean isRedeemed = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
