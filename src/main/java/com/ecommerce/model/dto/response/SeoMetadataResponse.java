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
public class SeoMetadataResponse {
    private Long id;
    private String entityType;
    private Long entityId;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private String ogTitle;
    private String ogDescription;
    private String ogImageUrl;
    private String canonicalUrl;
    private String robotsMeta;
    private String focusKeyword;
    private Integer seoScore;
    private boolean isIndexable;
    private boolean isFollowable;
    private LocalDateTime createdAt;
}
