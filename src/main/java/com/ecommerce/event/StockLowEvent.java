package com.ecommerce.event;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockLowEvent {

    private Long productId;
    private String productName;
    private String sku;
    private int currentStock;
    private int threshold;
    private String warehouseName;
    private java.time.LocalDateTime detectedAt;
}
