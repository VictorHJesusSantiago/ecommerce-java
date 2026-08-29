package com.ecommerce.model.dto.response.review;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImageResponse {
    private Long id;
    private String url;
    private String thumbnailUrl;
    private String altText;
    private int sortOrder;
}
