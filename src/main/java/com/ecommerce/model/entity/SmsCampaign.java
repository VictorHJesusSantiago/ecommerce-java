package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sms_campaigns")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "message")
    private String message;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "target_type")
    private String targetType;

    @Column(name = "target_ids", columnDefinition = "TEXT")
    private String targetIds;

    @Column(name = "total_recipients")
    private long totalRecipients;

    @Column(name = "total_sent")
    private long totalSent;

    @Column(name = "total_delivered")
    private long totalDelivered;

    @Column(name = "total_failed")
    private long totalFailed;

    @Column(name = "cost", precision = 10, scale = 4)
    private BigDecimal cost;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "created_by")
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
