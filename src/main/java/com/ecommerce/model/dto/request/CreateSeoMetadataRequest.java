package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSeoMetadataRequest {

    @NotBlank(message = "Entity type is required")
    private String entityType;

    @NotNull(message = "Entity ID is required")
    private Long entityId;

    @Size(max = 70)
    private String metaTitle;

    @Size(max = 160)
    private String metaDescription;

    @Size(max = 500)
    private String metaKeywords;

    @Size(max = 70)
    private String ogTitle;

    @Size(max = 300)
    private String ogDescription;

    @Size(max = 500)
    private String ogImageUrl;

    @Size(max = 500)
    private String canonicalUrl;

    @Size(max = 50)
    private String robotsMeta;

    @Size(max = 200)
    private String focusKeyword;

    private boolean isIndexable;
    private boolean isFollowable;
}
