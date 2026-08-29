package com.ecommerce.event;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockOutEvent {

    private Long productId;
    private String productName;
    private String sku;
    private String warehouseName;
    private java.time.LocalDateTime detectedAt;
}
