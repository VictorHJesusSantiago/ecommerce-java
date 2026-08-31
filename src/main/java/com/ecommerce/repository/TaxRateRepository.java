package com.ecommerce.repository;

import com.ecommerce.model.entity.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {

    @Query("SELECT tr FROM TaxRate tr WHERE tr.isActive = true AND tr.countryCode = :countryCode AND (tr.stateCode IS NULL OR tr.stateCode = :stateCode)")
    List<TaxRate> findApplicableRates(String countryCode, String stateCode);

    Optional<TaxRate> findByName(String name);
}
