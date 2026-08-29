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
public class SitemapEntryResponse {
    private Long id;
    private String url;
    private String type;
    private Long entityId;
    private BigDecimal priority;
    private String changefreq;
    private LocalDateTime lastmod;
    private boolean isActive;
    private boolean isIndexed;
}
