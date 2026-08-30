package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sitemap_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SitemapEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(nullable = false, length = 50)
    private String type;

    private Long entityId;

    @Column(precision = 3, scale = 1)
    private BigDecimal priority;

    @Column(length = 20)
    private String changefreq;

    private LocalDateTime lastmod;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private boolean isIndexed;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
