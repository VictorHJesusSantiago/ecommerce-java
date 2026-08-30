package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "loyalty_points", indexes = {
    @Index(name = "idx_lp_user", columnList = "user_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoyaltyPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int points = 0;

    @Column(nullable = false)
    private int lifetimePoints = 0;

    @Column(nullable = false)
    private int redeemedPoints = 0;

    private String tier = "BRONZE";

    private int tierPoints = 0;

    private LocalDateTime tierUpdatedAt;

    @Column(nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void addPoints(int points) {
        this.points += points;
        this.lifetimePoints += points;
        this.tierPoints += points;
        updateTier();
    }

    public boolean redeemPoints(int points) {
        if (this.points >= points) {
            this.points -= points;
            this.redeemedPoints += points;
            return true;
        }
        return false;
    }

    private void updateTier() {
        if (tierPoints >= 10000) {
            this.tier = "PLATINUM";
        } else if (tierPoints >= 5000) {
            this.tier = "GOLD";
        } else if (tierPoints >= 2000) {
            this.tier = "SILVER";
        } else {
            this.tier = "BRONZE";
        }
        this.tierUpdatedAt = LocalDateTime.now();
    }
}
