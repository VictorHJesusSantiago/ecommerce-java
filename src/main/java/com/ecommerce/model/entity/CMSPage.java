package com.ecommerce.model.entity;

import com.ecommerce.model.enums.CMSPageStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "cms_pages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class CMSPage extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 220)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "excerpt", length = 500)
    private String excerpt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private CMSPageStatus status = CMSPageStatus.DRAFT;

    @Column(name = "featured_image_url", length = 500)
    private String featuredImageUrl;

    @Column(name = "author_name", length = 100)
    private String authorName;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "meta_title", length = 200)
    private String metaTitle;

    @Column(name = "meta_description", length = 500)
    private String metaDescription;

    @Column(name = "meta_keywords", length = 300)
    private String metaKeywords;

    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    @Column(name = "allow_comments", nullable = false)
    @Builder.Default
    private Boolean allowComments = false;

    @Column(name = "template", length = 100)
    private String template;

    @Column(name = "is_homepage", nullable = false)
    @Builder.Default
    private Boolean isHomepage = false;
}
