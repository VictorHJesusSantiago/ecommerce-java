package com.ecommerce.service;

import com.ecommerce.model.entity.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {
    List<ExchangeRate> getAllExchangeRates();
    ExchangeRate getRate(String fromCurrency, String toCurrency);
    BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency);
    void refreshRates();
}
