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
public class SupportTicketResponse {
    private Long id;
    private String ticketNumber;
    private String subject;
    private String status;
    private String priority;
    private String category;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long assignedToId;
    private String assignedToName;
    private int repliesCount;
    private boolean isRead;
    private LocalDateTime lastReplyAt;
    private LocalDateTime closedAt;
    private LocalDateTime createdAt;
}
