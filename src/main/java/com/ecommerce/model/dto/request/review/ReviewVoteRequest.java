package com.ecommerce.model.dto.request.review;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewVoteRequest {

    private Long reviewId;

    private boolean isHelpful;
}
