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
public class TaxRateResponse {
    private Long id;
    private String name;
    private String country;
    private String state;
    private String zipCode;
    private String city;
    private BigDecimal rate;
    private boolean isCompound;
    private boolean isActive;
    private boolean includeTaxInPrice;
    private int priority;
    private String type;
    private LocalDateTime createdAt;
}
