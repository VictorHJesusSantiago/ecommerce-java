package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "content_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(unique = true, length = 200)
    private String slug;

    @Column(length = 1000)
    private String description;

    @Column(length = 50)
    private String type;

    @Column(length = 100)
    private String category;

    @Column(length = 500)
    private String thumbnailUrl;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private boolean isSystem;

    @Column(nullable = false)
    private int version;

    private long usageCount;

    @Column(length = 10000)
    private String content;

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
