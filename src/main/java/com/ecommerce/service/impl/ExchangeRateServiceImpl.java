package com.ecommerce.service.impl;

import com.ecommerce.model.entity.ExchangeRate;
import com.ecommerce.repository.ExchangeRateRepository;
import com.ecommerce.service.ExchangeRateService;
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
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ExchangeRate getRate(String fromCurrency, String toCurrency) {
        return exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) return amount;
        ExchangeRate rate = getRate(fromCurrency, toCurrency);
        if (rate == null) {
            log.warn("No exchange rate found for {} to {}", fromCurrency, toCurrency);
            return amount;
        }
        return amount.multiply(rate.getRate()).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    @Transactional
    public void refreshRates() {
        log.info("Refreshing exchange rates");
    }
}
