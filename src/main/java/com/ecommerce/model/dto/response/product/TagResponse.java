package com.ecommerce.model.dto.response.product;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TagResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String color;
    private boolean isActive;
    private Long productCount;
}
