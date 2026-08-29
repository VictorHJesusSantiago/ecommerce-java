package com.ecommerce.model.dto.response.product;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductImageResponse {

    private Long id;
    private String url;
    private String altText;
    private String title;
    private boolean isPrimary;
    private int sortOrder;
    private Integer width;
    private Integer height;
    private Long fileSize;
    private String contentType;
}
