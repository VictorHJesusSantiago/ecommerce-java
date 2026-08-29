package com.ecommerce.model.dto.response.review;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private Long productId;
    private String productName;
    private String productSlug;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Integer rating;
    private String title;
    private String comment;
    private String status;
    private boolean isVerifiedPurchase;
    private int helpfulCount;
    private int notHelpfulCount;
    private String sellerResponse;
    private LocalDateTime sellerResponseAt;
    private List<ReviewImageResponse> images;
    private boolean hasVoted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
