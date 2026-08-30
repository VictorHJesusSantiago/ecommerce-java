package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "seo_metadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeoMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String entityType;

    @Column(nullable = false)
    private Long entityId;

    @Column(length = 70)
    private String metaTitle;

    @Column(length = 160)
    private String metaDescription;

    @Column(length = 500)
    private String metaKeywords;

    @Column(length = 70)
    private String ogTitle;

    @Column(length = 300)
    private String ogDescription;

    @Column(length = 500)
    private String ogImageUrl;

    @Column(length = 500)
    private String canonicalUrl;

    @Column(length = 50)
    private String robotsMeta;

    @Column(length = 200)
    private String focusKeyword;

    private Integer seoScore;

    @Column(nullable = false)
    private boolean isIndexable;

    @Column(nullable = false)
    private boolean isFollowable;

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
