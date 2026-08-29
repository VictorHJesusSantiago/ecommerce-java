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
public class TicketMessageResponse {
    private Long id;
    private Long ticketId;
    private Long userId;
    private String userName;
    private String message;
    private boolean isStaff;
    private boolean isRead;
    private LocalDateTime createdAt;
}
