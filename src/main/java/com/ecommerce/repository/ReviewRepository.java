package com.ecommerce.repository;

import com.ecommerce.model.entity.Review;
import com.ecommerce.model.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByProductIdAndStatus(Long productId, ReviewStatus status, Pageable pageable);

    Page<Review> findByProductId(Long productId, Pageable pageable);

    Page<Review> findByUserId(Long userId, Pageable pageable);

    Optional<Review> findByProductIdAndUserId(Long productId, Long userId);

    boolean existsByProductIdAndUserId(Long productId, Long userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId AND r.status = 'APPROVED'")
    Double getAverageRatingByProductId(@Param("productId") Long productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId AND r.status = 'APPROVED'")
    long countApprovedByProductId(@Param("productId") Long productId);

    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.product.id = :productId AND r.status = 'APPROVED' GROUP BY r.rating ORDER BY r.rating DESC")
    List<Object[]> getRatingDistribution(@Param("productId") Long productId);

    List<Review> findByStatus(ReviewStatus status);

    @Query("SELECT r FROM Review r WHERE r.status = 'PENDING' ORDER BY r.createdAt ASC")
    List<Review> findPendingReviews();

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.status = 'APPROVED' ORDER BY r.helpfulCount DESC")
    List<Review> findMostHelpfulReviews(@Param("productId") Long productId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.isVerifiedPurchase = true")
    Page<Review> findVerifiedReviewsByUserId(@Param("userId") Long userId, Pageable pageable);

    long countByStatus(ReviewStatus status);
}
