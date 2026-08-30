package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(length = 50)
    private String appliesTo;

    @Column(length = 50)
    private String targetType;

    private Integer minimumQuantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal minimumAmount;

    private Integer maxUses;
    private long usageCount;

    @Column(length = 50)
    private String customerEligibility;

    private LocalDateTime startsAt;
    private LocalDateTime endsAt;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private int priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
