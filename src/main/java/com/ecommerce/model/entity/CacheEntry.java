package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cache_entries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cache_key")
    private String cacheKey;

    @Column(name = "cache_group")
    private String cacheGroup;

    @Column(name = "value", columnDefinition = "TEXT")
    private String value;

    @Column(name = "ttl")
    private int ttl;

    @Column(name = "hit_count")
    private long hitCount;

    @Column(name = "size_bytes")
    private long sizeBytes;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
