package com.ecommerce.model.entity;

import com.ecommerce.model.enums.ContentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "pages", indexes = {
    @Index(name = "idx_page_slug", columnList = "slug", unique = true),
    @Index(name = "idx_page_type", columnList = "contentType")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, unique = true, length = 280)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String excerpt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType contentType = ContentType.PAGE;

    private String template;

    private String featuredImageUrl;

    @Column(nullable = false)
    private boolean isPublished = false;

    private boolean isFeatured = false;

    private boolean allowComments = false;

    private int sortOrder = 0;

    private String metaTitle;

    private String metaDescription;

    private String metaKeywords;

    private String canonicalUrl;

    private String authorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    private int viewCount = 0;

    private String layout;

    private String customCss;

    private String customJs;

    private String templateData;

    private LocalDateTime publishedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
