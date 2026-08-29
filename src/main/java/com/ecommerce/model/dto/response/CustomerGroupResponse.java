package com.ecommerce.model.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGroupResponse {
    private Long id;
    private String name;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private long customersCount;
    private boolean isActive;
    private LocalDateTime createdAt;
}
