package com.ecommerce.event;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewCreatedEvent {

    private Long reviewId;
    private Long productId;
    private String productName;
    private Long userId;
    private String userName;
    private int rating;
    private java.time.LocalDateTime createdAt;
}
