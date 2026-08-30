package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "review_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class ReviewImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "alt_text", length = 200)
    private String altText;

    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;
}
