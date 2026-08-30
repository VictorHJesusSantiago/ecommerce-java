package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipping_zones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 10)
    private String country;

    @Column(length = 100)
    private String state;

    @Column(length = 20)
    private String zipCode;

    @Column(length = 100)
    private String city;

    @Column(precision = 10, scale = 2)
    private BigDecimal minOrderAmount;

    @Column(nullable = false)
    private boolean isActive;

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
