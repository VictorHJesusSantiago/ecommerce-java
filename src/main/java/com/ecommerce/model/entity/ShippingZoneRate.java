package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipping_zone_rates")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShippingZoneRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_zone_id", nullable = false)
    private ShippingZone shippingZone;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal rate;

    private BigDecimal minOrderAmount;

    private BigDecimal maxOrderAmount;

    private Double minWeight;

    private Double maxWeight;

    @Column(nullable = false)
    private boolean isActive = true;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
