package com.ecommerce.service.impl;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.entity.TaxRate;
import com.ecommerce.repository.TaxRateRepository;
import com.ecommerce.service.TaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {

    private final TaxRateRepository taxRateRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TaxRate> getAllTaxRates() {
        return taxRateRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TaxRate getTaxRateById(Long id) {
        return taxRateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaxRate", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTax(String country, String state, String zipCode, BigDecimal amount) {
        List<TaxRate> rates = taxRateRepository.findByCountryAndStateAndZipCode(country, state, zipCode);
        BigDecimal totalTax = BigDecimal.ZERO;
        for (TaxRate rate : rates) {
            BigDecimal tax = amount.multiply(rate.getRate()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            totalTax = totalTax.add(tax);
        }
        return totalTax;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxRate> getTaxRatesForLocation(String country, String state, String zipCode) {
        return taxRateRepository.findByCountryAndStateAndZipCode(country, state, zipCode);
    }
}
