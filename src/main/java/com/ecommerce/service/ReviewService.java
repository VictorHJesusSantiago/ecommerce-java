package com.ecommerce.service;

import com.ecommerce.model.dto.request.review.CreateReviewRequest;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.review.ReviewResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponse createReview(Long userId, CreateReviewRequest request);

    ReviewResponse updateReview(Long reviewId, Long userId, CreateReviewRequest request);

    void deleteReview(Long reviewId, Long userId);

    ReviewResponse getReviewById(Long id);

    PaginatedResponse<ReviewResponse> getProductReviews(Long productId, Pageable pageable);

    PaginatedResponse<ReviewResponse> getUserReviews(Long userId, Pageable pageable);

    void approveReview(Long reviewId);

    void rejectReview(Long reviewId);

    void flagReview(Long reviewId);

    ReviewResponse voteReview(Long reviewId, Long userId, boolean isHelpful);

    ReviewResponse respondToReview(Long reviewId, String response);
}
