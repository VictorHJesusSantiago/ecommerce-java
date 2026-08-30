package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist_items", indexes = {
    @Index(name = "idx_wi_wishlist", columnList = "wishlist_id"),
    @Index(name = "idx_wi_product", columnList = "product_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @Column(precision = 12, scale = 2)
    private BigDecimal priceAtAdd;

    private boolean notifyOnPriceDrop = false;

    private boolean notifyOnBackInStock = false;

    private boolean priceAlertSent = false;

    private boolean stockAlertSent = false;

    private String notes;

    private int priority = 0;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
