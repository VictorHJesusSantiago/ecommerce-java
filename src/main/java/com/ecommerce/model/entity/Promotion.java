package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class Promotion extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "promo_code", unique = true, length = 50)
    private String promoCode;

    @Column(name = "banner_image_url", length = 500)
    private String bannerImageUrl;

    @Column(name = "landing_page_url", length = 500)
    private String landingPageUrl;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "priority")
    @Builder.Default
    private Integer priority = 0;

    @Column(name = "max_uses")
    private Integer maxUses;

    @Column(name = "current_uses")
    @Builder.Default
    private Integer currentUses = 0;

    @Column(name = "target_url", length = 500)
    private String targetUrl;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;
}
