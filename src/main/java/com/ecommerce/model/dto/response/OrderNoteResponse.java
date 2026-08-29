package com.ecommerce.model.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderNoteResponse {
    private Long id;
    private String note;
    private boolean isCustomerNote;
    private boolean isPublic;
    private String createdBy;
    private LocalDateTime createdAt;
}
