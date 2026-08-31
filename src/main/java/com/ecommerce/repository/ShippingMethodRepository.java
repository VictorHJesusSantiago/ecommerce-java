package com.ecommerce.repository;

import com.ecommerce.model.entity.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Long> {

    List<ShippingMethod> findByIsActiveTrue();

    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.isActive = true AND sm.isFreeShippingEligible = true")
    List<ShippingMethod> findFreeShippingMethods();

    @Query("SELECT sm FROM ShippingMethod sm JOIN sm.zones z WHERE sm.isActive = true AND z.id = :zoneId")
    List<ShippingMethod> findByZoneId(Long zoneId);
}
