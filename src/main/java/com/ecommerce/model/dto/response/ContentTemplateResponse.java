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
public class ContentTemplateResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String type;
    private String category;
    private String thumbnailUrl;
    private boolean isActive;
    private boolean isSystem;
    private int version;
    private long usageCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
