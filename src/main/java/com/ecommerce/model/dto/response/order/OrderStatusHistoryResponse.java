package com.ecommerce.model.dto.response.order;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderStatusHistoryResponse {

    private Long id;
    private String previousStatus;
    private String newStatus;
    private String comment;
    private String changedByName;
    private boolean isSystemGenerated;
    private boolean customerNotified;
    private LocalDateTime createdAt;
}
