package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_price_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPriceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal oldPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal newPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal oldCompareAtPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal newCompareAtPrice;

    @Column(length = 50)
    private String changeType;

    @Column(length = 500)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by_id")
    private User changedBy;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
