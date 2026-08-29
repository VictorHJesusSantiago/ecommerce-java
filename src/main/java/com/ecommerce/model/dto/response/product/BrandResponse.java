package com.ecommerce.model.dto.response.product;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BrandResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private String bannerUrl;
    private String websiteUrl;
    private boolean isActive;
    private boolean isFeatured;
    private int sortOrder;
    private Long productCount;
    private List<String> categories;
}
