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
public class EmailLogResponse {
    private Long id;
    private String toEmail;
    private String fromEmail;
    private String subject;
    private String templateName;
    private String status;
    private String type;
    private String referenceType;
    private Long referenceId;
    private LocalDateTime sentAt;
    private LocalDateTime openedAt;
    private LocalDateTime clickedAt;
    private LocalDateTime createdAt;
}
