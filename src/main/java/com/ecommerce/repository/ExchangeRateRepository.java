package com.ecommerce.repository;

import com.ecommerce.model.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findByFromCurrencyAndToCurrency(String from, String to);

    @Query("SELECT er FROM ExchangeRate er WHERE er.isActive = true")
    java.util.List<ExchangeRate> findActiveRates();
}
