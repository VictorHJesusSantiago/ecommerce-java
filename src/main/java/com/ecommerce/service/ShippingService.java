package com.ecommerce.service;

import com.ecommerce.model.entity.ShippingMethod;
import com.ecommerce.model.entity.ShippingZone;

import java.math.BigDecimal;
import java.util.List;

public interface ShippingService {
    List<ShippingZone> getAllShippingZones();
    ShippingZone getShippingZoneById(Long id);
    List<ShippingMethod> getAllShippingMethods();
    ShippingMethod getShippingMethodById(Long id);
    List<ShippingMethod> getShippingMethodsForZone(Long zoneId);
    BigDecimal calculateShippingCost(Long methodId, BigDecimal weight, BigDecimal total);
    List<ShippingMethod> getAvailableShippingMethods(String address, String country, String state, String zipCode);
}
