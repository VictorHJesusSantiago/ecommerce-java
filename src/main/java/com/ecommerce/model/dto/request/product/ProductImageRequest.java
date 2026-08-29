package com.ecommerce.model.dto.request.product;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductImageRequest {

    private Long id;

    private String url;

    private String altText;

    private String title;

    private boolean isPrimary;

    private int sortOrder;

    private String variantOptionValue;

    private String imageUrl;
}
