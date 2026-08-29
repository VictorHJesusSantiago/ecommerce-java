package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSitemapEntryRequest {

    @NotBlank(message = "URL is required")
    @Size(min = 1, max = 500)
    private String url;

    @NotBlank(message = "Type is required")
    private String type;

    private Long entityId;

    private BigDecimal priority;

    private String changefreq;

    private boolean isActive;
    private boolean isIndexed;
}
