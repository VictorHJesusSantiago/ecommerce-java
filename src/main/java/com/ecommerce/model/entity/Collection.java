package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "collections", indexes = {
    @Index(name = "idx_collection_slug", columnList = "slug", unique = true)
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 120)
    private String slug;

    private String description;

    private String imageUrl;

    private String bannerUrl;

    private String metaTitle;

    private String metaDescription;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private boolean isFeatured = false;

    private int sortOrder = 0;

    private Long productCount = 0L;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "collections")
    @Builder.Default
    private Set<Product> products = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
