package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    private long totalViews;
    private long dailyViews;
    private long weeklyViews;
    private long monthlyViews;

    private long totalSold;
    private long dailySold;
    private long weeklySold;
    private long monthlySold;

    @Column(precision = 12, scale = 2)
    private BigDecimal totalRevenue;

    @Column(precision = 12, scale = 2)
    private BigDecimal dailyRevenue;

    @Column(precision = 12, scale = 2)
    private BigDecimal weeklyRevenue;

    @Column(precision = 12, scale = 2)
    private BigDecimal monthlyRevenue;

    private long totalCartAdds;
    private long totalWishlistAdds;

    @Column(precision = 5, scale = 2)
    private Double conversionRate;

    private LocalDateTime lastViewedAt;
    private LocalDateTime lastSoldAt;

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
