package com.ecommerce.event;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductCreatedEvent {

    private Long productId;
    private String productName;
    private String sku;
    private java.math.BigDecimal price;
    private Long categoryId;
    private Long brandId;
    private java.time.LocalDateTime createdAt;
}
