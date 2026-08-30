package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "review_votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class ReviewVote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_helpful", nullable = false)
    private Boolean isHelpful;
}
