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
public class ImportJobResponse {
    private Long id;
    private String name;
    private String type;
    private String fileName;
    private String status;
    private int totalRows;
    private int processedRows;
    private int successfulRows;
    private int failedRows;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Long durationMs;
    private LocalDateTime createdAt;
}
