package com.ecommerce.model.entity;

import com.ecommerce.model.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reviews", indexes = {
    @Index(name = "idx_review_product", columnList = "product_id"),
    @Index(name = "idx_review_user", columnList = "user_id"),
    @Index(name = "idx_review_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private BigDecimal rating;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private ReviewStatus status = ReviewStatus.PENDING;

    @Column(name = "is_verified_purchase", nullable = false)
    @Builder.Default
    private Boolean isVerifiedPurchase = false;

    @Column(name = "helpful_count")
    @Builder.Default
    private Integer helpfulCount = 0;

    @Column(name = "not_helpful_count")
    @Builder.Default
    private Integer notHelpfulCount = 0;

    @Column(name = "admin_reply", length = 1000)
    private String adminReply;

    @Column(name = "admin_reply_at")
    private java.time.LocalDateTime adminReplyAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ReviewImage> images = new HashSet<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ReviewVote> votes = new HashSet<>();
}
