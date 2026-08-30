package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "webhooks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Webhook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "secret")
    private String secret;

    @Column(name = "events", columnDefinition = "TEXT")
    private String events;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "headers", columnDefinition = "TEXT")
    private String headers;

    @Column(name = "retry_count")
    private int retryCount;

    @Column(name = "timeout")
    private int timeout;

    @Column(name = "last_triggered_at")
    private LocalDateTime lastTriggeredAt;

    @Column(name = "last_status_code")
    private Integer lastStatusCode;

    @Column(name = "last_response", columnDefinition = "TEXT")
    private String lastResponse;

    @Column(name = "failure_count")
    private long failureCount;

    @Column(name = "success_count")
    private long successCount;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
