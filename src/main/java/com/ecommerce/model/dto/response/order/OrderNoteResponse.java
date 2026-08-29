package com.ecommerce.model.dto.response.order;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderNoteResponse {

    private Long id;
    private String message;
    private String authorName;
    private boolean isCustomerVisible;
    private boolean isInternal;
    private LocalDateTime createdAt;
}
