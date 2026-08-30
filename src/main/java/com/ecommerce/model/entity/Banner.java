package com.ecommerce.model.entity;

import com.ecommerce.model.enums.BannerPosition;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "banners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class Banner extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 500)
    private String subtitle;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "mobile_image_url", length = 500)
    private String mobileImageUrl;

    @Column(name = "link_url", length = 500)
    private String linkUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private BannerPosition position;

    @Column(name = "alt_text", length = 200)
    private String altText;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    @Column(name = "target_blank", nullable = false)
    @Builder.Default
    private Boolean targetBlank = false;
}
