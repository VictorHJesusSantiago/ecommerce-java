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
public class ExchangeRateResponse {
    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal rate;
    private String source;
    private LocalDateTime lastUpdated;
    private boolean isActive;
}
