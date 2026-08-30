package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversion_tracking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50)
    private String sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(precision = 10, scale = 2)
    private BigDecimal orderTotal;

    @Column(length = 200)
    private String firstTouchSource;

    @Column(length = 200)
    private String lastTouchSource;

    @Column(length = 200)
    private String utmSource;

    @Column(length = 200)
    private String utmMedium;

    @Column(length = 200)
    private String utmCampaign;

    @Column(length = 500)
    private String landingPage;

    @Column(length = 500)
    private String referrerUrl;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
