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
public class ProductVideoResponse {
    private Long id;
    private Long productId;
    private String title;
    private String url;
    private String thumbnailUrl;
    private String type;
    private Integer duration;
    private int sortOrder;
    private boolean isActive;
    private LocalDateTime createdAt;
}
