package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_campaigns")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 200)
    private String subject;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(length = 500)
    private String recipientSegment;

    private long totalRecipients;
    private long totalSent;
    private long totalDelivered;
    private long totalOpened;
    private long totalClicked;
    private long totalBounced;

    @Column(precision = 5, scale = 2)
    private BigDecimal openRate;

    @Column(precision = 5, scale = 2)
    private BigDecimal clickRate;

    @Column(precision = 5, scale = 2)
    private BigDecimal bounceRate;

    @Column(length = 5000)
    private String htmlContent;

    @Column(length = 5000)
    private String textContent;

    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;

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
