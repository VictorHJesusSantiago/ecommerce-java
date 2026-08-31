package com.ecommerce.service;

import com.ecommerce.model.entity.TaxRate;

import java.math.BigDecimal;
import java.util.List;

public interface TaxService {
    List<TaxRate> getAllTaxRates();
    TaxRate getTaxRateById(Long id);
    BigDecimal calculateTax(String country, String state, String zipCode, BigDecimal amount);
    List<TaxRate> getTaxRatesForLocation(String country, String state, String zipCode);
}
