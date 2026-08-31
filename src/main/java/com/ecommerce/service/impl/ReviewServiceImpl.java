package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.Review;
import com.ecommerce.model.enums.ReviewStatus;
import com.ecommerce.model.dto.request.review.CreateReviewRequest;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.review.ReviewResponse;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.ReviewService;
import com.ecommerce.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    @Override
    @Transactional
    public ReviewResponse createReview(Long userId, CreateReviewRequest request) {
        var product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId()));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (reviewRepository.existsByProductIdAndUserId(request.getProductId(), userId)) {
            throw new com.ecommerce.exception.BadRequestException("You have already reviewed this product");
        }

        Review review = Review.builder()
                .product(product)
                .user(user)
                .rating(request.getRating())
                .title(request.getTitle())
                .comment(request.getComment())
                .status(ReviewStatus.PENDING)
                .build();

        Review saved = reviewRepository.save(review);

        updateProductRating(request.getProductId());

        return entityMapper.toReviewResponse(saved);
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(Long reviewId, Long userId, CreateReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        if (!review.getUser().getId().equals(userId)) {
            throw new com.ecommerce.exception.ForbiddenException("You can only edit your own reviews");
        }

        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setComment(request.getComment());
        review.setStatus(ReviewStatus.PENDING);

        Review saved = reviewRepository.save(review);
        updateProductRating(review.getProduct().getId());

        return entityMapper.toReviewResponse(saved);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        if (!review.getUser().getId().equals(userId)) {
            throw new com.ecommerce.exception.ForbiddenException("You can only delete your own reviews");
        }

        Long productId = review.getProduct().getId();
        reviewRepository.delete(review);
        updateProductRating(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        return entityMapper.toReviewResponse(review);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<ReviewResponse> getProductReviews(Long productId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByProductIdAndStatus(productId, ReviewStatus.APPROVED, pageable);
        return PaginatedResponse.of(
                reviews.getContent().stream().map(entityMapper::toReviewResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), reviews.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<ReviewResponse> getUserReviews(Long userId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByUserId(userId, pageable);
        return PaginatedResponse.of(
                reviews.getContent().stream().map(entityMapper::toReviewResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), reviews.getTotalElements()
        );
    }

    @Override
    @Transactional
    public void approveReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        review.setStatus(ReviewStatus.APPROVED);
        reviewRepository.save(review);
        updateProductRating(review.getProduct().getId());
    }

    @Override
    @Transactional
    public void rejectReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        review.setStatus(ReviewStatus.REJECTED);
        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void flagReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        review.setStatus(ReviewStatus.FLAGGED);
        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public ReviewResponse voteReview(Long reviewId, Long userId, boolean isHelpful) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        if (isHelpful) {
            review.setHelpfulCount(review.getHelpfulCount() + 1);
        } else {
            review.setNotHelpfulCount(review.getNotHelpfulCount() + 1);
        }

        Review saved = reviewRepository.save(review);
        return entityMapper.toReviewResponse(saved);
    }

    @Override
    @Transactional
    public ReviewResponse respondToReview(Long reviewId, String response) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        review.setSellerResponse(response);
        review.setSellerResponseAt(java.time.LocalDateTime.now());
        Review saved = reviewRepository.save(review);
        return entityMapper.toReviewResponse(saved);
    }

    private void updateProductRating(Long productId) {
        Double avgRating = reviewRepository.getAverageRatingByProductId(productId);
        Long reviewCount = reviewRepository.countApprovedByProductId(productId);

        var product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setAverageRating(avgRating != null ? avgRating : 0.0);
            product.setReviewCount(reviewCount);
            productRepository.save(product);
        }
    }
}
