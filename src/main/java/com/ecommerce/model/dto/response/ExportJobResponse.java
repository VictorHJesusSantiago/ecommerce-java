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
public class ExportJobResponse {
    private Long id;
    private String name;
    private String type;
    private String format;
    private String status;
    private String fileName;
    private Long fileSize;
    private int totalRows;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime expiresAt;
    private Long durationMs;
    private LocalDateTime createdAt;
}
