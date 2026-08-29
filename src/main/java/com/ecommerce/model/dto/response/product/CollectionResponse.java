package com.ecommerce.model.dto.response.product;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CollectionResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private String bannerUrl;
    private boolean isActive;
    private boolean isFeatured;
    private int sortOrder;
    private Long productCount;
}
