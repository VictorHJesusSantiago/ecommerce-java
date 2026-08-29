package com.ecommerce.controller.api.v1;

import com.ecommerce.model.dto.request.review.CreateReviewRequest;
import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.review.ReviewResponse;
import com.ecommerce.service.ReviewService;
import com.ecommerce.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Review management APIs")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Create a review")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @Valid @RequestBody CreateReviewRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        ReviewResponse response = reviewService.createReview(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Review created", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a review")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody CreateReviewRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        ReviewResponse response = reviewService.updateReview(id, userId, request);
        return ResponseEntity.ok(ApiResponse.success("Review updated", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        reviewService.deleteReview(id, userId);
        return ResponseEntity.ok(ApiResponse.success("Review deleted"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReviewById(@PathVariable Long id) {
        ReviewResponse response = reviewService.getReviewById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get product reviews")
    public ResponseEntity<ApiResponse<PaginatedResponse<ReviewResponse>>> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PaginatedResponse<ReviewResponse> response = reviewService.getProductReviews(
                productId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/vote")
    @Operation(summary = "Vote on review helpfulness")
    public ResponseEntity<ApiResponse<ReviewResponse>> voteReview(
            @PathVariable Long id,
            @RequestParam boolean helpful) {
        Long userId = SecurityUtils.getCurrentUserId();
        ReviewResponse response = reviewService.voteReview(id, userId, helpful);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/respond")
    @Operation(summary = "Respond to a review")
    public ResponseEntity<ApiResponse<ReviewResponse>> respondToReview(
            @PathVariable Long id,
            @RequestBody String response_text) {
        ReviewResponse response = reviewService.respondToReview(id, response_text);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/my-reviews")
    @Operation(summary = "Get current user's reviews")
    public ResponseEntity<ApiResponse<PaginatedResponse<ReviewResponse>>> getMyReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Long userId = SecurityUtils.getCurrentUserId();
        PaginatedResponse<ReviewResponse> response = reviewService.getUserReviews(
                userId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
